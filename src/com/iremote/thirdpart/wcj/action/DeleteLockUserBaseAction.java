package com.iremote.thirdpart.wcj.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.common.SendThirdpartHelper;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;

public class DeleteLockUserBaseAction {
	private static Log log = LogFactory.getLog(DeleteLockUserBaseAction.class);
	
	protected int zwavedeviceid;
	protected int usercode = 0x01;
	protected String tid;
	protected int asynch;
	
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	
	protected ZWaveDevice lock;
	protected int doorlockpasswordusertype;
	protected int timeoutsecond = 10 ;
	
	public String execute(){
		setDoorlockpasswordusertype(doorlockpasswordusertype);

		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);
		if (lock == null) {
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		if (asynch != 0&&!check())
			return Action.SUCCESS;
		DoorlockPasswordService dps = new DoorlockPasswordService();
		DoorlockPassword doorlockpassword ;
		if(asynch==IRemoteConstantDefine.SEND_PASSWORD_TYPE_ASYNCH){
			if(StringUtils.isNotBlank(tid)||(usercode & 0xff) == 0xff){
				doorlockpassword = dps.queryLatestActivePassword2(zwavedeviceid,doorlockpasswordusertype, tid);
			}else{
				doorlockpassword = dps.queryLatestActivePassword2(zwavedeviceid,doorlockpasswordusertype,usercode);
			}
		}else{
			if(StringUtils.isNotBlank(tid)||(usercode & 0xff) == 0xff){
				doorlockpassword = dps.queryLatestActivePassword3(zwavedeviceid,doorlockpasswordusertype, tid);
			}else{
				doorlockpassword = dps.queryLatestActivePassword3(zwavedeviceid,doorlockpasswordusertype,usercode);
			}
		}
		
		
		if(doorlockpassword!=null){
			usercode = doorlockpassword.getUsercode();
		}
		if(asynch==IRemoteConstantDefine.SEND_PASSWORD_TYPE_ASYNCH){
			if(doorlockpassword!=null){
				if(directDeleteDB(doorlockpassword)){
					doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
					doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
					doorlockpassword.setErrorcount(0);
					doorlockpassword.setSendtime(new Date());
					new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS,
							zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
							IRemoteConstantDefine.DELETE_LOCK_USER_RESULT).sendThirdpartMsg();
				}else{
					doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
					doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING);
					doorlockpassword.setErrorcount(0);
					if(checkIfOnline()){
						if(deleteLockUser()){
							doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
							doorlockpassword.setSendtime(new Date());
							new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS,
									zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
									IRemoteConstantDefine.DELETE_LOCK_USER_RESULT).sendThirdpartMsg();
						}else if(!checkDoorLockUser()){
							resultCode = ErrorCodeDefine.SUCCESS;
							doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
							doorlockpassword.setSendtime(new Date());
							new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS,
									zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
									IRemoteConstantDefine.DELETE_LOCK_USER_RESULT).sendThirdpartMsg();
						}else if(resultCode == ErrorCodeDefine.DEVICE_OFFLINE){
							resultCode = ErrorCodeDefine.SUCCESS;
						}
					}
				}
				resultCode = ErrorCodeDefine.SUCCESS;
			}else if(deleteLockUser()){
				new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS,
						zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
						IRemoteConstantDefine.DELETE_LOCK_USER_RESULT).sendThirdpartMsg();
			}else {
				resultCode = ErrorCodeDefine.CANNOT_FIND_DOORLOCKUSER;
			}
		}else{
			if(doorlockpassword!=null){
				doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
				doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING);
				if(deleteLockUser()){
					doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
					doorlockpassword.setSendtime(new Date());
					new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS,
							zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
							IRemoteConstantDefine.DELETE_LOCK_USER_RESULT).sendThirdpartMsg();
				}else if(!checkDoorLockUser()){
					resultCode = ErrorCodeDefine.SUCCESS;
					doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
					doorlockpassword.setSendtime(new Date());
					new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS,
							zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
							IRemoteConstantDefine.DELETE_LOCK_USER_RESULT).sendThirdpartMsg();
				}
			}else{
				if(!deleteLockUser()&&!checkDoorLockUser()){
					resultCode = ErrorCodeDefine.SUCCESS;
				}
			}
		}
		return Action.SUCCESS;
	}
	
	private boolean directDeleteDB(DoorlockPassword doorlockpassword){
		if(doorlockpassword.getSynstatus()==2&&doorlockpassword.getStatus()==1){
			return false;
		}
		if(doorlockpassword.getSynstatus()==3&&doorlockpassword.getStatus()==9){
			return false;
		}
		if(doorlockpassword.getSynstatus()==4||doorlockpassword.getSynstatus()==5){
			return false;
		}
		return true;
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
	
	protected boolean deleteLockUser() {
		return false;
	}
	protected boolean checkDoorLockUser(){
		return true;
	}
	protected boolean check() {
		
		if (asynch != 0 && lock.getStatus() != null && lock.getStatus() == -1) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		if (asynch != 0 && ConnectionManager.isOnline(lock.getDeviceid()) == false) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		return true;
	}
	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public void setAsynch(int asynch) {
		this.asynch = asynch;
	}
	public void setDoorlockpasswordusertype(int doorlockpasswordusertype) {
		this.doorlockpasswordusertype = doorlockpasswordusertype;
	}

}
