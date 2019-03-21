package com.iremote.action.gateway;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Remote;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class ModifyGatewayPageAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid ;
	private String name ;
	
	public String execute()
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if ( r == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		name = r.getName();

		return Action.SUCCESS;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public String getName() {
		return name;
	}
	
	
}
