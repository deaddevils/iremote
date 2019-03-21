package com.iremote.action.device.doorlock;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.Doorlockalarmphone;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.service.DoorlockUserService;

public class DoorlockPasswordUserSetter implements IDoorlockOperationProcessor
{
	private static Log log = LogFactory.getLog(DoorlockPasswordUserSetter.class);

	public static String[] STATUS = new String[]{"" , "init", "gatewayoffline","sendingpassword" , "failed" , "success" , "devicebussing"};
	public static int STATUS_INIT = 1 ;
	public static int STATUS_OFFLINE = 2 ;
	public static int STATUS_SENDING_PASSWORD = 3 ;
	public static int STATUS_FAILED = 4 ;
	public static int STATUS_SUCCESS = 5 ;
	public static int STATUS_DEVICE_BUSSING = 6 ;
			
	private String deviceid ;
	private int nuid ;
	private int usercode = 0xFF;
	private int usertype ;
	private String username ;
	private String password;
	private String validfrom;
	private String validthrough;
	private Date dvalidfrom ;
	private Date dvalidthrough;
	private int zwavedeviceid ;
	private Date localtime ;
	private int status = STATUS_INIT ;
	private boolean hassendpassword = false ;
	private boolean hassendvalidtime = false ;
	private boolean haswakeuped = false ;
	private IAsyncResponse sendpasswordresponse ;
	
	private long expiretime = System.currentTimeMillis() + 30 * 1000;
	private static Byte[] REPORT_SEND_PASSWORD = new Byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x03};
	private static Byte[] REPORT_SEND_VALID_TIME = new Byte[]{(byte)0x80,0x07,0x00,0x70,0x10,0x01,0x06};
	private byte USER_TYPE_PASSWORD = 0x01 ;
	
	private List<String> alarmphone;
	private List<String> countrycode;

	
	@Override
	public int getStatus()
	{
		if ( status == STATUS_SENDING_PASSWORD )
		{
			if ( sendpasswordresponse != null )
			{
				Integer rs = DoorlockPasswordHelper.checkResponse(sendpasswordresponse);
				
				if ( rs == null )
				{
					if( expiretime - System.currentTimeMillis() < 5 * 1000 // device did not response , maybe is sleeping, ask user to wake up device.
						&& haswakeuped == false ) 							// has not asked user to wake up
						
					{
						if ( DeviceHelper.isZwavedevice(nuid) )	//zwave device can't wake up , return gateway offline message and expire in 3 seconds
						{
							expiretime = System.currentTimeMillis() + 3 * 1000; 
							status = STATUS_OFFLINE;
							return status ;
						}
						expiretime = System.currentTimeMillis() + 30 * 1000;
						haswakeuped = true ;
						status = STATUS_OFFLINE;
						hassendpassword = false ;
						ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, REPORT_SEND_PASSWORD, this);
					}
				}
				else if ( rs == ErrorCodeDefine.GATEWAY_ERROR_CODE_BUSSING)
					status = STATUS_DEVICE_BUSSING;
				else if ( rs != ErrorCodeDefine.SUCCESS )
					status = STATUS_FAILED;
			}
		}
		return status ;
	}
	
	@Override
	public String getMessage()
	{
		return STATUS[getStatus()];
	}
	
	@Override
	public void init()
	{
		regist();
		checkGatewayStatus();
	}
	
	@Override
	public void sendcommand()
	{
		sendPassord();
	}
	
	private void regist()
	{
		GatewayEventConsumerStore.getInstance().put(deviceid, this);
		DoorlockOperationStore.getInstance().put(String.valueOf(zwavedeviceid), this);
	}
	
	public void unregistReportConsumer()
	{
		ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid,REPORT_SEND_PASSWORD, this);
		ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid,REPORT_SEND_VALID_TIME, this);
		GatewayEventConsumerStore.getInstance().remove(deviceid, this);
		DoorlockOperationStore.getInstance().remove(String.valueOf(zwavedeviceid));
	}

	private void checkGatewayStatus()
	{
		if ( !ConnectionManager.contants(deviceid))
			status = STATUS_OFFLINE;
	}
	
	private synchronized void sendPassord()
	{
		if ( hassendpassword == true )
			return ;
		hassendpassword = true;
		
		ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, REPORT_SEND_PASSWORD, this);
		
		byte[] b = DoorlockPasswordHelper.createSetLockUserPasswordCommand((byte)usercode, password, null);
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(b, nuid);
		sendpasswordresponse = SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 10);

		expiretime = System.currentTimeMillis() + 15 * 1000;
		status = STATUS_SENDING_PASSWORD;
	}
	
	private synchronized void sendValidtime()
	{
		if ( hassendvalidtime == true )
			return ;
		hassendvalidtime = true;
		
		ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, REPORT_SEND_VALID_TIME, this);
		
		Calendar cs = Calendar.getInstance();
		Calendar ce = Calendar.getInstance();
		try{
			if ( StringUtils.isBlank(validfrom))
				validfrom = "2001-01-01 00:00";
			dvalidfrom =Utils.parseMin(validfrom);
			cs.setTime(dvalidfrom);

			if ( StringUtils.isBlank(validthrough))
				validthrough = "2099-12-31 23:59";
			this.dvalidthrough =Utils.parseMin(validthrough);
			ce.setTime(dvalidthrough);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		byte[] b = DoorlockPasswordHelper.createSetValidTimeCommand((byte)usercode, cs, ce, USER_TYPE_PASSWORD);
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(b, nuid);
		sendpasswordresponse = null ; //avoid problems
		SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 10);

		expiretime = System.currentTimeMillis() + 15 * 1000;
	}
	
	@Override
	public void onGatewayOnline()
	{	
		sendPassord();
	}

	@Override
	public void onGatewayOffline()
	{		
	}
	
	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report)
	{
		if ( Utils.isByteMatch(REPORT_SEND_PASSWORD, report) && report.length > 8)
		{
			usercode = report[7] & 0xff;
			
			ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid,REPORT_SEND_PASSWORD, this);
			
			if ( GatewayUtils.isCobbeLock(deviceid) 
					&& Utils.isLockTempPassordforSMSSend(usercode))
				return ;
			
			int rst = report[8];
			if ( rst != 1 )
			{
				status = STATUS_FAILED;
				return ;
			}

			sendValidtime();
		}
		else if ( Utils.isByteMatch(REPORT_SEND_VALID_TIME, report))
		{
			int rst = report[10];
			if ( rst != 1 )
			{
				status = STATUS_FAILED;
				return ;
			}
			DoorlockUserService dus = new DoorlockUserService();
			dus.delete(zwavedeviceid, usertype, usercode);
			
			DoorlockUser du = new DoorlockUser();
			du.setUsercode(usercode);
			du.setUsername(username);
			du.setUsertype(usertype);
			du.setZwavedeviceid(zwavedeviceid);
			du.setValidfrom(dvalidfrom);
			du.setValidthrough(dvalidthrough);
			saveAlarmPhone(du);
			dus.save(du);
			
			status = STATUS_SUCCESS;
			DoorlockHelper.sendCurrentTime(deviceid, nuid , this.localtime);
			
			if (status == STATUS_SUCCESS) {
				sendToThirdPart();
			}
		}
	}
	private void sendToThirdPart() {
		JMSUtil.sendmessage(IRemoteConstantDefine.ADD_LOCK_USER_RESULT, createZwaveDeviceEvent());
	}

	private ZWaveDeviceEvent createZwaveDeviceEvent() {
		ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
		zde.setZwavedeviceid(zwavedeviceid);
		zde.setDeviceid(deviceid);
		zde.setEventtime(new Date());
		zde.setEventtype(IRemoteConstantDefine.ADD_LOCK_USER_RESULT);
		zde.setAppendmessage(createAppendMessage());
		zde.setValidfrom(dvalidfrom);
		zde.setValidthrough(dvalidthrough);
		zde.setName(username);
		zde.setUsertype(IRemoteConstantDefine.DOOR_LOCK_USER_PASSWORD);
		return zde;
	}

	private JSONObject createAppendMessage() {
		JSONObject json = new JSONObject();
		json.put("resultCode", IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS);
		if (status == STATUS_SUCCESS) {
			json.put("userCode", usercode);
		}
		return json;
	}
	public void saveAlarmPhone(DoorlockUser du){
		if(alarmphone != null && alarmphone.size() > 0){
			for(int i=0; i < alarmphone.size(); i++){
				if(alarmphone.get(i) != null && alarmphone.get(i).length() > 0){
					Doorlockalarmphone dc = new Doorlockalarmphone();
					dc.setAlarmphone(alarmphone.get(i));
					dc.setDoorlockuser(du);
					dc.setCountrycode(countrycode.get(i));
					du.getDoorlockalarmphone().add(dc);
					du.setAlarmtype(IRemoteConstantDefine.DOORLOCK_USER_TYPE_ALARM);
				}
			}
		}
	}
	
	@Override
	public boolean isFinished()
	{
		return ( status == STATUS_FAILED || status == STATUS_SUCCESS || status == STATUS_DEVICE_BUSSING );
	}

	public int getUsercode()
	{
		return usercode;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}

	@Override
	public long getExpireTime()
	{
		return expiretime;
	}

	public void setUsertype(int usertype)
	{
		this.usertype = usertype;
	}

	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}

	public void setValidthrough(String validthrough) {
		this.validthrough = validthrough;
	}

	public void setAlarmphone(List<String> alarmphone) {
		this.alarmphone = alarmphone;
	}

	public void setCountrycode(List<String> countrycode) {
		this.countrycode = countrycode;
	}

	public void setLocaltime(Date localtime)
	{
		this.localtime = localtime;
	}

}
