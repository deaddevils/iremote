package com.iremote.thirdpart.wcj.action;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DoorlockUser;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.DoorlockUserService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class SetFingerprintBase extends AddLockUserBaseAction{
	protected String fingerprint;
	protected List<DoorlockPassword> activefinger;
	protected DoorlockPasswordService dpsvr = new DoorlockPasswordService();
	private static Log log = LogFactory.getLog(SetFingerprintBase.class);

	public SetFingerprintBase() {
		super();
		asynch = 1;
	}

	@Override
	protected boolean check() {
		if (asynch != 0 && lock.getStatus() != null && lock.getStatus() == -1) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		if (asynch != 0 && ConnectionManager.isOnline(lock.getDeviceid()) == false) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		if (checkfingerprint() == false) {
			resultCode = ErrorCodeDefine.DOORLOCK_SETPASSWORD_CLASH;
			return false;
		}
		return true;

	}

	protected boolean checkfingerprint() {
		return true;
	}

	@Override
	protected boolean sendUserToLock(DoorlockPassword doorlockpassword) {
		SetLockFingerprintUserHelper fingerprinthelper = new SetLockFingerprintUserHelper();
		JSONObject sendCommand = fingerprinthelper.sendCommand(zwavedeviceid, fingerprint, validfrom, validthrough, weekday, starttime, endtime);
		net.sf.json.JSONObject setresult = net.sf.json.JSONObject.fromObject(sendCommand);
		resultCode = setresult.getInt("resultCode");
		log.info("set fingerprint resultcode is "+resultCode);
		if(resultCode==ErrorCodeDefine.SUCCESS){
			usercode = setresult.getInt("usercode") & 0xff;
			saveDoorlockUser();
			return true;
		}
		return false;
	}
	
	private void saveDoorlockUser() {
		DoorlockUserService dus = new DoorlockUserService();
		dus.delete(zwavedeviceid, IRemoteConstantDefine.DOOR_LOCK_USER_FINGERPRINT, usercode);

		DoorlockUser du = new DoorlockUser();
		if (StringUtils.isBlank(username))
		username = String.format("%s%d",
					MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_FINGER_USER, 0,
							IRemoteConstantDefine.DEFAULT_LANGUAGE),
					usercode);
		du.setUsername(username);
		du.setUsertype(IRemoteConstantDefine.DOOR_LOCK_USER_FINGERPRINT);
		du.setUsercode(usercode);
		du.setZwavedeviceid(zwavedeviceid);
		du.setValidfrom(validfrom);
		du.setValidthrough(validthrough);
		du.setWeekday(weekday);
		du.setStarttime(starttime);
		du.setEndtime(endtime);
		du.setCardid(0);

		dus.save(du);
		
	}

	@Override
	protected boolean sendValidTimeToLock(DoorlockPassword doorlockpassword) {
		return true;
	}
	@Override
	protected void uniqueMethod(DoorlockPassword doorlockPassword) {
	}
	@Override
	protected String createpassword() {
		return fingerprint;
	}
	@Override
	public void setUsertype(int usertype) {
		this.usertype = IRemoteConstantDefine.DOORLOCKUSER_TYPE_FINGERPRINT;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	
}
