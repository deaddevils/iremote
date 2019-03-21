package com.iremote.action.device.authorize;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.ZWaveDeviceShareService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class DeleteDevicePrivilegeAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid ;
	private int zwavedeviceshareid ;
	
	public String execute()
	{
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		ZWaveDeviceShare zds = zdss.queryByid(zwavedeviceshareid);
		
		if ( zds.getZwavedeviceid() != zwavedeviceid )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		zdss.delete(zds);
		
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	public void setZwavedeviceshareid(int zwavedeviceshareid)
	{
		this.zwavedeviceshareid = zwavedeviceshareid;
	}

	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}
}
