package com.iremote.thirdpart.wcj.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;

public class UpdateLockUserValidTimeAction {
	private int zwavedeviceid;
	private String validfrom;
	private String validthrough;
	private String tid;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private static Log log = LogFactory.getLog(UpdateLockUserValidTimeAction.class);
	private ZWaveDevice lock;
	
	public String execute(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			format.parse(validfrom);
			format.parse(validthrough);
		} catch (Exception e){
			log.error("Wrong validity of the door lock user by tid:"+tid+",and zwavedeviceid:"+zwavedeviceid,e);
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);
	
		if (lock == null) {
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		DoorlockPasswordService dps = new DoorlockPasswordService();
		DoorlockPassword doorlockpassword = dps.findByTidAndZwavedeviceid(tid,zwavedeviceid);
		if(doorlockpassword==null){
			log.error("can not find doorlockuser by tid:"+tid+",and zwavedeviceid:"+zwavedeviceid);
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		Date datefrom = Utils.parseTime(validfrom);
		Date dateto = Utils.parseTime(validthrough);
		doorlockpassword.setValidfrom(datefrom);
		doorlockpassword.setValidthrough(dateto);
		doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
		doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE);
		doorlockpassword.setErrorcount(0);
		
		return Action.SUCCESS;
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

	public void setTid(String tid) {
		this.tid = tid;
	}
	
}
