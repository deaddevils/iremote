package com.iremote.action.devicegroup;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.DeviceGroup;
import com.iremote.domain.PhoneUser;
import com.iremote.service.DeviceGroupService;
import com.opensymphony.xwork2.Action;

public class OperateDeviceGroupAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int devicegroupid;
	private String command;
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

		DeviceGroupExecutor dge = new DeviceGroupExecutor(phoneuser , dg , command);
		dge.run();
		
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
	public void setCommand(String command)
	{
		this.command = command;
	}
	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
	
}
