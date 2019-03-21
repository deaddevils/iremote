package com.iremote.thirdpart.wcj.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.encrypt.AES;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DoorlockUser;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.DoorlockUserService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class SetPasswordBase extends AddLockUserBaseAction{
	protected String password;
	protected String superpw;
	protected String encryptedpassword;
	protected DoorlockPasswordService dpsvr = new DoorlockPasswordService();
	protected List<DoorlockPassword> activepassword;
	private static Log log = LogFactory.getLog(SetPasswordBase.class);
	
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
		encryptedpassword = AES.encrypt2Str(password);

		if (checkpassword() == false) {
			resultCode = ErrorCodeDefine.DOORLOCK_SETPASSWORD_CLASH;
			return false;
		}
		return true;
	}
	
	protected boolean checkpassword() {
		return true;
	}

	@Override
	protected void uniqueMethod(DoorlockPassword doorlockPassword) {
		doorlockPassword.setPasswordtype(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_TYPE_NORMAL);
	}
	@Override
	protected String createpassword() {
		return encryptedpassword;
	}
	@Override
	protected boolean sendUserToLock(DoorlockPassword doorlockpassword) {
		if(!checkIfOnline()){
			return false;
		}
		Pair<Integer,Integer> p = DoorlockHelper.sendPassword((byte)usercode, password, lock);
		resultCode = p.getLeft();
		
		if (resultCode == ErrorCodeDefine.SUCCESS) {
			usercode = p.getRight() & 0xff;
			if(StringUtils.isNotBlank(username)){
				saveDoorlockUser();
			}
			return true;
		}
		return false;
	}
	private boolean checkIfOnline() {
		if (lock == null) {
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false;
		}
		if (lock.getStatus() != null && lock.getStatus() == -1) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		if (ConnectionManager.isOnline(lock.getDeviceid()) == false) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		return true;
	}
	private void saveDoorlockUser() {
		DoorlockUserService dus = new DoorlockUserService();
		dus.delete(zwavedeviceid, IRemoteConstantDefine.DOOR_LOCK_USER_PASSWORD, usercode);

		DoorlockUser du = new DoorlockUser();
		du.setUsername(username);
		du.setUsertype(IRemoteConstantDefine.DOOR_LOCK_USER_PASSWORD);
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
		resultCode = DoorlockHelper.sendTimeConfigure(lock.getDeviceid(), lock.getNuid(), (byte)usertype, (byte)usercode, validfrom, validthrough);
		if (resultCode == ErrorCodeDefine.SUCCESS) {
			if((checkIfChuangjia()&&weekday==0)||(!checkIfChuangjia()&&weekday==128)){
				return true;
			}
			resultCode = DoorlockHelper.sendWeekTimeConfig(lock.getDeviceid(), lock.getNuid(), (byte)usertype, (byte)usercode,weekday,starttime,endtime);
			if(resultCode == ErrorCodeDefine.SUCCESS){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void setUsertype(int usertype) {
		this.usertype = IRemoteConstantDefine.DOORLOCKUSER_TYPE_PASSWORD;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSuperpw(String superpw) {
		this.password = superpw;
	}
	
}
