package com.iremote.thirdpart.wcj.action;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;


@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY , domain = "device", parameter = "zwavedeviceid")
public class SetUserPasswordWithoutDate {
	
	private static Log log = LogFactory.getLog(SetUserPasswordWithoutDate.class);
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid;
	private String password;
	private int usercode = 0xff;
	
	private DoorlockPasswordService dpsvr = new DoorlockPasswordService();
	private DoorlockPassword doorlockpassword;
	private List<DoorlockPassword> activepassword;
	private String encryptedpassword ;
	private ZWaveDevice lock;
	
	public String execute()
	{
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);
		
		if ( check() == false )
			return Action.SUCCESS;
		
		createpassword();

		Pair<Integer , Integer> p = DoorlockHelper.sendPassword((byte)usercode, password, lock);
		
		this.resultCode = p.getLeft();
		if ( this.resultCode == ErrorCodeDefine.SUCCESS)
		{
			usercode = p.getRight();
			doorlockpassword.setUsercode(usercode);
			if(lock.getNuid()<256){
				doorlockpassword.setLocktype(1);
			}else{
				doorlockpassword.setLocktype(2);
			}
			dpsvr.save(doorlockpassword);
			updateActivePassword();
		}

		return Action.SUCCESS;
	}

	private boolean check()
	{
		if ( lock == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false;
		}
		
		if (  lock.getStatus() != null && lock.getStatus() == -1 )
		{
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		
		if ( ConnectionManager.isOnline(lock.getDeviceid()) == false )
		{
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		
		encryptedpassword = AES.encrypt2Str(password);
		
		if ( checkpassword() == false )
		{
			resultCode = ErrorCodeDefine.DOORLOCK_SETPASSWORD_CLASH;
			return false;
		}
		return true;
	}
	
	private boolean checkpassword()
	{
		activepassword = dpsvr.queryActivePassword(zwavedeviceid);
		if ( activepassword == null || activepassword.size() == 0 )
			return true;
		for ( DoorlockPassword dp : activepassword )
		{
			if ( dp.getPassword().equals(encryptedpassword) )
				return false ;
		}
		return true;
	}
	
	private void createpassword()
	{
		doorlockpassword = new DoorlockPassword();
		doorlockpassword.setZwavedeviceid(zwavedeviceid);
		doorlockpassword.setPassword(encryptedpassword);
		doorlockpassword.setValidfrom(Utils.parseTime("2010-01-01 00:00:00"));
		doorlockpassword.setValidthrough(Utils.parseTime("2099-01-01 00:00:00"));
		doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE);
		doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
		doorlockpassword.setUsercode(usercode & 0xff);

	}
	
	private void updateActivePassword()
	{
		if ( activepassword == null || activepassword.size() == 0 )
			return;
		for ( DoorlockPassword dp : activepassword )
		{
			if ( dp.getUsercode() == this.usercode)
				dp.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ECLIPSED);
		}
	}
	
	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}

	public int getUsercode()
	{
		return usercode;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
