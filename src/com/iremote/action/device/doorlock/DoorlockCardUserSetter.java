package com.iremote.action.device.doorlock;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.Card;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.Doorlockalarmphone;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.service.CardService;
import com.iremote.service.DoorlockUserService;

public class DoorlockCardUserSetter implements IDoorlockOperationProcessor
{
	private static Log log = LogFactory.getLog(DoorlockCardUserSetter.class);
	
	public static String[] STATUS = new String[]{"" , "init", "gatewayoffline","inputcard","sendingcommand" , "failed" , "success", "devicebussing" , "cardinuse" , "outofmemery" , "cardexpired" , "clonecar"};
	public static int STATUS_INIT = 1 ;
	public static int STATUS_OFFLINE = 2 ;
	public static int STATUS_INPUT_CARD = 3 ;
	public static int STATUS_SENDING_COMMAN = 4 ;
	public static int STATUS_FAILED = 5 ;
	public static int STATUS_SUCCESS = 6 ;
	public static int STATUS_DEVICE_BUSSING = 7 ;
	public static int STATUS_CARD_IN_USE = 8 ;
	public static int STATUS_DEVICE_OUTOFMEMERY = 9 ;
	public static int STATUS_CARD_EXPIRED = 10 ;
	public static int STATUS_CARD_CLONE = 11 ;

	private String deviceid ;
	private int nuid ;
	private int zwavedeviceid ;
	private String validfrom;
	private String validthrough;
	private Date localtime ;
	private int status = STATUS_INIT ;
	private long expiretime = System.currentTimeMillis() + 30 * 1000;
//	private boolean hassendvalidtime = false;
	private boolean hassendstartcommand = false ;
	
	private static Byte[] REPORT_CARD_REPORT = new Byte[]{(byte)0x80,0x09,0x07};
	private static Byte[] REPORT_ADD_CARD_REPORT = new Byte[]{(byte)0x80,0x09,0x02};
	private static Byte[] REPORT_REMOTE_ADD_CARD_REPORT = new Byte[]{(byte)0x80,0x09,0x09};
//	private static Byte[] REPORT_SEND_VALID_TIME = new Byte[]{(byte)0x80,0x07,0x00,0x70,0x10,0x01,0x06};

	private byte[] startaddcardcommand = new byte[]{(byte)0x80 , 0x09,0x08,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0x80,0x00,0x00,0x01,0x01,0x01,0x00,99,12,30,23} ;

	private byte[] cardinfo;
	private byte cardtype = 1 ;	
	private String username ;
	private int usertype ;
	private int usercode ;
//	private byte USER_TYPE_CARD = 0x02;
	
	private List<String> alarmphone;
	private List<String> countrycode;

	@Override
	public void init()
	{
		DoorlockOperationStore.getInstance().put(String.valueOf(zwavedeviceid), this);
		ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, REPORT_CARD_REPORT, this);
		status = STATUS_INPUT_CARD;

		if ( !ConnectionManager.contants(deviceid))
		{
			status = STATUS_OFFLINE;
			GatewayEventConsumerStore.getInstance().put(deviceid, this);
		}
		else 
			sendstartcommand();
	}

	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report)
	{
		if ( Utils.isByteMatch(REPORT_CARD_REPORT, report) && report.length > 14 )
		{
			switch ( report[14])
			{
			case 3:
				ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, REPORT_CARD_REPORT, this);
				ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, REPORT_ADD_CARD_REPORT, this);
				cardinfo = new byte[8];
				for ( int i = 0 ; i < cardinfo.length ; i ++ )
					cardinfo[i] = report[i + 6];
				cardtype = report[5] ;

				sendcommand();
				break;
				
			case 0:
				status = STATUS_CARD_IN_USE;
				break;
			case 1:
				status = STATUS_CARD_EXPIRED;
				break;
			case 2:
				status = STATUS_CARD_CLONE;
				break;
			}
			
		}
		else if( Utils.isByteMatch(REPORT_ADD_CARD_REPORT, report) 
				|| Utils.isByteMatch(REPORT_REMOTE_ADD_CARD_REPORT , report))
		{
			ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, REPORT_ADD_CARD_REPORT, this);
			ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, REPORT_REMOTE_ADD_CARD_REPORT, this);
			if ( report[3] == 1 )
			{
				status = STATUS_SUCCESS;
				usercode = (report[4] & 0xff) * 256 + (report[5] & 0xff); 
				//sendValidtime();
				saveDoorlockUser();
				
				DoorlockHelper.sendCurrentTime(deviceid, nuid , this.localtime);
			}
			else if ( report[3] == 0 )
				status = STATUS_CARD_IN_USE;
			else if ( report[3] == 2 )
				status = STATUS_DEVICE_OUTOFMEMERY;
			
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
		zde.setValidfrom(Utils.parseTime(validfrom));
		zde.setValidthrough(Utils.parseTime(validthrough));
		zde.setName(username);
		zde.setUsertype(IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD_10);
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
//	private synchronized void sendValidtime()
//	{
//		if ( hassendvalidtime == true )
//			return ;
//		hassendvalidtime = true;
//		
//		ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, REPORT_SEND_VALID_TIME, this);
//		
//		Calendar cs = Calendar.getInstance();
//		Calendar ce = Calendar.getInstance();
//		
//		try{
//			if(validfrom != null && validfrom.length() > 0){
//				Date date =Utils.parseMin(validfrom);
//				cs.setTime(date);
//			}
//			if(validthrough != null && validthrough.length() > 0){
//				Date date =Utils.parseMin(validthrough);
//				ce.setTime(date);
//			}
//		}catch(Exception e){
//			log.error(e.getMessage(), e);
//		}
//		
//		byte[] b = DoorlockPasswordHelper.createSetValidTimeCommand((byte)usercode, cs, ce, USER_TYPE_CARD);
//		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(b, nuid);
//		SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 10);
//
//		expiretime = System.currentTimeMillis() + 15 * 1000;
//	}
	
	private void saveDoorlockUser()
	{
		Card c = null ;
		if ( cardinfo != null )
		{
			String sha1key = DigestUtils.sha1Hex(cardinfo);
			
			CardService cs = new CardService();
			c = cs.queryCardbykey(sha1key, Utils.getRemotePlatform(deviceid));
			
			if ( c == null )
			{
				c = new Card();
				c.setSha1key(sha1key);
				c.setThirdpartid(Utils.getRemotePlatform(deviceid));
				
				cs.save(c);
			}
		}
		
		DoorlockUserService dus = new DoorlockUserService();
//		dus.delete(zwavedeviceid, usertype, usercode);
		
		DoorlockUser du = new DoorlockUser();
 		du.setUsername(username);
		du.setUsertype(usertype);
		du.setUsercode(usercode);
		du.setZwavedeviceid(zwavedeviceid);
		du.setValidfrom(validfrom);
		du.setValidthrough(validthrough);
		if ( c != null )
			du.setCardid(c.getCardid());
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
	
	private void setvalidtime(byte[] command)
	{
		if ( StringUtils.isBlank(validfrom) )
			validfrom = "2001-01-01 00:00";

		Date vf = Utils.parseMin(validfrom);
		Calendar cs = Calendar.getInstance();
		cs.setTime(vf);
		command[17] = (byte)(cs.get(Calendar.YEAR) - 2000 );
		command[18] = (byte)(cs.get(Calendar.MONTH) + 1 );
		command[19] = (byte)cs.get(Calendar.DAY_OF_MONTH);
		command[20] = (byte)cs.get(Calendar.HOUR_OF_DAY);
		
		
		if ( StringUtils.isBlank(this.validthrough) )
			validthrough = "2099-12-31 23:59";

		Date vt = Utils.parseMin(validthrough);
		Calendar ce = Calendar.getInstance();
		ce.setTime(vt);
		command[21] = (byte)(ce.get(Calendar.YEAR) - 2000 );
		command[22] = (byte)(ce.get(Calendar.MONTH) + 1 );
		command[23] = (byte)ce.get(Calendar.DAY_OF_MONTH);
		command[24] = (byte)ce.get(Calendar.HOUR_OF_DAY);

	}
	
	@Override
	public long getExpireTime()
	{
		return expiretime;
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
	public boolean isFinished()
	{
		boolean f = ( status == STATUS_FAILED 
				|| status == STATUS_SUCCESS  
				|| status == STATUS_CARD_IN_USE
				|| status == STATUS_DEVICE_OUTOFMEMERY
				|| status == STATUS_CARD_EXPIRED
				|| status == STATUS_CARD_CLONE);
		
		if ( f )
		{
			ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, REPORT_CARD_REPORT, this);
			ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, REPORT_ADD_CARD_REPORT, this);
			ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, REPORT_REMOTE_ADD_CARD_REPORT, this);
			//DoorlockOperationStore.getInstance().remove(String.valueOf(zwavedeviceid));
			GatewayEventConsumerStore.getInstance().remove(deviceid, this);
		}
		
		return f ;
	}


	@Override
	public void sendcommand()
	{
		if ( cardinfo == null )
			return ;
		byte[] b = DoorlockPasswordHelper.createAddCardCommand(cardtype, cardinfo);
		setvalidtime(b);
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(b, nuid);
		SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 10);
		expiretime = System.currentTimeMillis() + 15 * 1000;
		status = STATUS_SENDING_COMMAN;
	}
	
	private void sendstartcommand()
	{
		if ( hassendstartcommand == true )
			return ;
		hassendstartcommand = true ;
		
		ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, REPORT_REMOTE_ADD_CARD_REPORT, this);
		
		setvalidtime(startaddcardcommand);
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(startaddcardcommand, nuid);
		SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 10);
	}
	
	@Override
	public void onGatewayOnline()
	{
		status = STATUS_INPUT_CARD;
		expiretime = System.currentTimeMillis() + 30 * 1000;
		GatewayEventConsumerStore.getInstance().remove(deviceid, this);
		
		sendstartcommand();
	}

	@Override
	public void onGatewayOffline()
	{
		
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public void setUsertype(int usertype)
	{
		this.usertype = usertype;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
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
