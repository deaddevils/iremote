package com.iremote.action.devicegroup;

import java.util.Date;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.DeviceGroup;
import com.iremote.domain.PhoneUser;
import com.iremote.service.DeviceGroupService;
import com.opensymphony.xwork2.Action;

public class DeleteDeviceGroupAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int devicegroupid;
	private PhoneUser phoneuser ;
	
	public String execute()
	{
		DeviceGroupService dgs = new DeviceGroupService();
		
		DeviceGroup dg = dgs.get(devicegroupid);
		if ( dg == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		if ( dg.getPhoneuserid() != phoneuser.getPhoneuserid())
		{
			this.resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		dgs.delete(dg);
		
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "deletedevicegroup");
		if ( phoneuser != null )
			phoneuser.setLastupdatetime(new Date());

		return Action.SUCCESS;
		
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setDevicegroupid(int devicegroupid)
	{
		this.devicegroupid = devicegroupid;
	}
	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
}
