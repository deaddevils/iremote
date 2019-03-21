package com.iremote.action.device.doorlock;

import java.util.Set;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class AddLockUserPageAction
{
	private int usertype ;
	private int zwavedeviceid ;
	private Set<Integer> capablitycode ;
	private int platform;
	private PhoneUser phoneuser;
	
	public String execute()
	{		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(zwavedeviceid);
		
		capablitycode = DeviceHelper.initDeviceCapability(zd);
		if(phoneuser!=null){
			platform = phoneuser.getPlatform();
		}
		return Action.SUCCESS;
	}

	public boolean isSupportValidTime()
	{
		return  ( ( usertype == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_PASSWORD &&  capablitycode.contains(IRemoteConstantDefine.DOOR_LOCK_CAPABILITY_PASSWORD_TIME) )
				|| ( usertype == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_FINGERPRINT && capablitycode.contains(IRemoteConstantDefine.DOOR_LOCK_CAPABILITY_FINGERPRINT_TIME) )
				|| ( usertype == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_CARD && capablitycode.contains(IRemoteConstantDefine.DOOR_LOCK_CAPABILITY_CARD_TIME)) );

	}

	public int getZwavedeviceid() {
		return zwavedeviceid;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public int getUsertype()
	{
		return usertype;
	}

	public void setUsertype(int usertype)
	{
		this.usertype = usertype;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
}
