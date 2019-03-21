package com.iremote.action.device.doorlock;

import com.iremote.common.ErrorCodeDefine;
import com.opensymphony.xwork2.Action;

public class GetLockOperationStatusAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String zwavedeviceid ;
	private String status ;
	
	public String execute()
	{
		IDoorlockOperationProcessor setter = (IDoorlockOperationProcessor)DoorlockOperationStore.getInstance().get(zwavedeviceid);
		
		if ( setter == null )
		{
			status = DoorlockPasswordUserSetter.STATUS[DoorlockPasswordUserSetter.STATUS_FAILED];
			return Action.SUCCESS;
		}
		
		status = setter.getMessage();
				
		return Action.SUCCESS;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public String getStatus()
	{
		return status;
	}

	public void setZwavedeviceid(String zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	
	
}
