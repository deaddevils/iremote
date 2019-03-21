package com.iremote.action.device.doorlock;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class EditLockUserAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String username ;
	private int doorlockuserid;
	
	private PhoneUser phoneuser ;
	
	public String execute()
	{
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.query(doorlockuserid);
		
		if ( du == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(du.getZwavedeviceid());
		
		if ( zd == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			dus.delete(du);
			return Action.SUCCESS;
		}
		
		if ( PhoneUserHelper.checkPrivilege(phoneuser, zd) == false )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		du.setUsername(username);
		
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public void setDoorlockuserid(int doorlockuserid)
	{
		this.doorlockuserid = doorlockuserid;
	}
	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	
	
}
