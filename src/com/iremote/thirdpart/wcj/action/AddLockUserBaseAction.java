package com.iremote.thirdpart.wcj.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DoorlockHelper;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.common.SendThirdpartHelper;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;

public class AddLockUserBaseAction{
	private static Log log = LogFactory.getLog(AddLockUserBaseAction.class);

	protected int zwavedeviceid;
	protected String validfrom;
	protected String validthrough;
	protected int weekday = 128; 
	protected String starttime = "00:00";
	protected String endtime = "23:59";
	protected int asynch;
	protected String tid;
	protected String username;
	protected String cardname;
	
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	protected int usercode = 0xFF;
	protected int usertype;
	protected ZWaveDevice lock;
	 
	public String execute(){
		setUsertype(usertype);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (StringUtils.isBlank(validfrom)){
			validfrom = "2010-01-01 00:00:00";
		}
		if (StringUtils.isBlank(validthrough)){
			validthrough = "2099-01-01 00:00:00";
		}
		try{
			format.parse(validfrom);
			format.parse(validthrough);
		} catch (Exception e){
			log.error("Wrong validity of the door lock user",e);
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);

		if (lock == null) {
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if(checkIfChuangjia() && weekday==128){
			weekday = 0 ;
		}
		
		if (!check())
			return Action.SUCCESS;
		DoorlockPasswordService dps = new DoorlockPasswordService();
		DoorlockPassword doorlockPassword = new DoorlockPassword();
		doorlockPassword.setZwavedeviceid(zwavedeviceid);
		doorlockPassword.setUsertype(getUsertype());
		
		Date datefrom = Utils.parseTime(validfrom);
		Date dateto = Utils.parseTime(validthrough);
		doorlockPassword.setValidfrom(datefrom);
		doorlockPassword.setValidthrough(dateto);
		doorlockPassword.setWeekday(weekday);
		doorlockPassword.setStarttime(starttime);
		doorlockPassword.setEndtime(endtime);
		doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING);
		doorlockPassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE);
		doorlockPassword.setCreatetime(new Date());
		doorlockPassword.setTid(tid);
		doorlockPassword.setPassword(createpassword());
		doorlockPassword.setUsername(username);
		if(StringUtils.isBlank(username)&&StringUtils.isNotBlank(cardname)){
			doorlockPassword.setUsername(cardname);
		}
		uniqueMethod(doorlockPassword);
		
		if(lock.getNuid()<256){
			doorlockPassword.setLocktype(1);
		}else{
			doorlockPassword.setLocktype(2);
		}

		doorlockPassword.setUsercode(usercode & 0xff);
		if(asynch==IRemoteConstantDefine.SEND_PASSWORD_TYPE_ASYNCH){
			dps.save(doorlockPassword);
		}
		boolean paresult = sendUserToLock(doorlockPassword);
		if(paresult){
			doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
			boolean tiresult = sendValidTimeToLock(doorlockPassword);
			if(tiresult){
				sendCurrentTime(lock);
				if(asynch==IRemoteConstantDefine.SEND_PASSWORD_TYPE_ASYNCH){
					doorlockPassword.setUsercode(usercode & 0xff);
					doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
					doorlockPassword.setSendtime(new Date());
					new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS,
							zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
							IRemoteConstantDefine.ADD_LOCK_USER_RESULT).sendThirdpartMsg();
					//ThirdPartHelper.sendAddThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS, zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid, new Date());
				}
				resultCode = ErrorCodeDefine.SUCCESS;
			}else{
				if(asynch==IRemoteConstantDefine.SEND_PASSWORD_TYPE_ASYNCH){
					doorlockPassword.setUsercode(usercode & 0xff);
					doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
					new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_SUCCESS_DATE_FAIL,
							zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
							IRemoteConstantDefine.ADD_LOCK_USER_RESULT).sendThirdpartMsg();
					//ThirdPartHelper.sendAddThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_SUCCESS_DATE_FAIL, zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid, new Date());
					resultCode = ErrorCodeDefine.SUCCESS;
				}else{
					resultCode = ErrorCodeDefine.DOORLOCK_PASSWORDSUCCESS_VALIDTIMEFAILED;
				}
			}
		}else{
			if(asynch==IRemoteConstantDefine.SEND_PASSWORD_TYPE_ASYNCH){
				doorlockPassword.setUsercode(usercode & 0xff);
				resultCode = ErrorCodeDefine.SUCCESS;
			}else{
				//DOORLOCK_SETPASSWORD_FAILED;
			}
		}
		
		return Action.SUCCESS;
	}

	protected boolean checkIfChuangjia(){
		if ( StringUtils.isNotBlank(lock.getProductor())
				&& lock.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX.toLowerCase())
				&& !lock.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX_2.toLowerCase())){
			return true;
		}
		return false;
	}
	
	private void sendCurrentTime(ZWaveDevice zwavedevice) {
		Date dc = new Date();
		String tzid = GatewayHelper.getRemoteTimezoneId(zwavedevice.getDeviceid());
		if (StringUtils.isNotBlank(tzid))
			dc = TimeZoneHelper.timezoneTranslate(dc, TimeZone.getDefault(), TimeZone.getTimeZone(tzid));

		DoorlockHelper.sendCurrentTime(zwavedevice.getDeviceid(), zwavedevice.getNuid(), dc);
		
	}

	protected void uniqueMethod(DoorlockPassword doorlockPassword) {
	}

	protected String createpassword() {
		return null;
	}

	protected boolean check() {
		return false;
	}
	
	protected boolean sendValidTimeToLock(DoorlockPassword doorlockPassword) {
		return false;
	}

	protected boolean sendUserToLock(DoorlockPassword doorlockPassword) {
		return false;
	}

	protected int getUsertype(){
		return usertype;
	}
	
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}

	public void setValidthrough(String validthrough) {
		this.validthrough = validthrough;
	}

	public void setSuperpwtimes(String superpwtimes) {
		this.validfrom = superpwtimes;
	}

	public void setSuperpwtimee(String superpwtimee) {
		this.validthrough = superpwtimee;
	}
	
	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public void setAsynch(int asynch) {
		this.asynch = asynch;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}

	public int getUsercode() {
		return usercode;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}
	
}
