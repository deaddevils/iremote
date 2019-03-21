package com.iremote.action.device.doorlock;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.Doorlockalarmphone;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.service.DoorlockUserService;

public class DoorlockFingerprintUserSetter implements IDoorlockOperationProcessor
{
	private static Log log = LogFactory.getLog(DoorlockFingerprintUserSetter.class);
	
	public static String[] STATUS = new String[]{"" , "init", "gatewayoffline","sendingcommand" ,"inputfingerprint","inputfingerprintagain" , "failed" , "success", "devicebussing"};
	public static int STATUS_INIT = 1 ;
	public static int STATUS_OFFLINE = 2 ;
	public static int STATUS_SENDING_COMMAN = 3 ;
	public static int STATUS_INPUT_FINGERPRINT = 4 ;
	public static int STATUS_INPUT_FINGERPRINT_AGAIN = 5 ;
	public static int STATUS_FAILED = 6 ;
	public static int STATUS_SUCCESS = 7 ;
	public static int STATUS_DEVICE_BUSSING = 8 ;
	
	private String deviceid ;
	private int nuid ;
	private int usertype ;
	private int usercode ;
	private String username ;
	private String validfrom;
	private String validthrough;
	private Date dvalidfrom ;
	private Date dvalidthrough;
	private int zwavedeviceid ;
	private Date localtime ;
	private int status = STATUS_INIT ;
	private boolean hassendcommand = false ;
	private boolean haswakeuped = false ;
	
	private long expiretime = System.currentTimeMillis() + 60 * 1000;
	private static Byte[] REPORT_SET_FINGERPRINT = new Byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x08};
	private static Byte[] REPORT_SEND_VALID_TIME = new Byte[]{(byte)0x80,0x07,0x00,0x70,0x10,0x01,0x06};
	private byte USER_TYPE_FINGERPRINT = 0x03 ;

	private boolean hassendvalidtime = false ;
	
	private List<String> alarmphone;
	private List<String> countrycode;

	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report)
	{		
		if ( status == STATUS_SUCCESS || status == STATUS_FAILED)
			return ;

		int rst = report[8] ;
		
		if ( rst == 0x01 )
		{
			usercode = report[7] & 0xff;
			sendValidtime();
			status = STATUS_SUCCESS;
			saveDoorlcokUser();
			DoorlockHelper.sendCurrentTime(deviceid, nuid , this.localtime);
		}
		else if ( rst == 0x03 )
		{
			expiretime = System.currentTimeMillis() + 15 * 1000;
			status = STATUS_INPUT_FINGERPRINT_AGAIN;
		}
		else 
			status = STATUS_FAILED;

		sendToThirdPart();
	}

	private void sendToThirdPart() {
		JMSUtil.sendmessage(IRemoteConstantDefine.ADDING_FINGER_PRINT_USER_STATUS, createZwaveDeviceEvent());
	}

	private ZWaveDeviceEvent createZwaveDeviceEvent() {
		ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
		zde.setZwavedeviceid(zwavedeviceid);
		zde.setDeviceid(deviceid);
		zde.setEventtime(new Date());
		zde.setEventtype(IRemoteConstantDefine.ADDING_FINGER_PRINT_USER_STATUS);
		zde.setAppendmessage(createAppendMessage());
		zde.setValidfrom(dvalidfrom);
		zde.setValidthrough(dvalidthrough);
		zde.setName(username);
		return zde;
	}

	private JSONObject createAppendMessage() {
		JSONObject json = new JSONObject();
		json.put("resultCode", status);
		if (status == STATUS_SUCCESS) {
			json.put("userCode", usercode);
		}
		return json;
	}

	private synchronized void sendValidtime()
	{
		if ( hassendvalidtime == true )
			return ;
		hassendvalidtime = true;
		
		ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, REPORT_SEND_VALID_TIME,IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND*3, this);
		
		Calendar cs = Calendar.getInstance();
		Calendar ce = Calendar.getInstance();
		
		try{
			if ( StringUtils.isBlank(validfrom))
				validfrom = "2001-01-01 00:00";
			dvalidfrom =Utils.parseMin(validfrom);
			cs.setTime(dvalidfrom);

			if ( StringUtils.isBlank(validthrough))
				validthrough = "2099-12-31 23:59";
			dvalidthrough =Utils.parseMin(validthrough);
			ce.setTime(dvalidthrough);

		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		byte[] b = DoorlockPasswordHelper.createSetValidTimeCommand((byte)usercode, cs, ce, USER_TYPE_FINGERPRINT);
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(b, nuid);
		SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 10);

		expiretime = System.currentTimeMillis() + 15 * 1000;
	}
	
	private void saveDoorlcokUser()
	{
		DoorlockUserService dus = new DoorlockUserService();
		dus.delete(zwavedeviceid, usertype, usercode);
		
		DoorlockUser du = new DoorlockUser();
 		du.setUsername(username);
		du.setUsertype(usertype);
		du.setUsercode(usercode);
		du.setZwavedeviceid(zwavedeviceid);
		du.setValidfrom(dvalidfrom);
		du.setValidthrough(dvalidthrough);
		saveAlarmPhone(du);
		dus.save(du);
		
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
	public long getExpireTime()
	{
		return expiretime;
	}

	@Override
	public void onGatewayOnline()
	{
		sendcommand();
	}

	@Override
	public void onGatewayOffline()
	{

	}

	@Override
	public int getStatus()
	{
		return status;
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
	public synchronized void sendcommand()
	{
		if ( hassendcommand == true )
			return ;
		hassendcommand = true;
		
		status = STATUS_SENDING_COMMAN;
		expiretime = System.currentTimeMillis() + 15 * 1000;
		
		ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, REPORT_SET_FINGERPRINT,IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND*3, this);
		
		byte[] b = DoorlockPasswordHelper.createInputFingerprintCommand();
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(b, nuid);
		byte[] rst = SynchronizeRequestHelper.synchronizeRequest(deviceid, ct, 10);

		if ( rst == null )
		{
			if( haswakeuped == false )
			{
				if ( DeviceHelper.isZwavedevice(nuid) )	//zwave device can't wake up , return gateway offline message and expire in 3 seconds
				{
					expiretime = System.currentTimeMillis() + 3 * 1000; 
					status = STATUS_OFFLINE;
					return ;
				}

				expiretime = System.currentTimeMillis() + 30 * 1000;
				haswakeuped = true ;
				status = STATUS_OFFLINE;
				hassendcommand = false ;
				ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, REPORT_SET_FINGERPRINT, this);
			}
		}
	   	else if ( TlvWrap.readInt(rst , TagDefine.TAG_RESULT , TlvWrap.TAGLENGTH_LENGTH) == ErrorCodeDefine.SUCCESS )
		{
			expiretime = System.currentTimeMillis() + 15 * 1000;
			status = STATUS_INPUT_FINGERPRINT;
		}
		else 
			status = STATUS_FAILED ;
		
	}
	
	@Override
	public boolean isFinished()
	{
		return ( status == STATUS_FAILED || status == STATUS_SUCCESS );
	}
	
	private void regist()
	{
		GatewayEventConsumerStore.getInstance().put(deviceid, this);
		DoorlockOperationStore.getInstance().put(String.valueOf(zwavedeviceid), this);
	}
	
	private void checkGatewayStatus()
	{
		if ( !ConnectionManager.contants(deviceid))
			status = STATUS_OFFLINE;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}

	public void setUsertype(int usertype)
	{
		this.usertype = usertype;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
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
