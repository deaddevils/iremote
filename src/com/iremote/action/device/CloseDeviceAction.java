package com.iremote.action.device;

import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameters = {"zwavedeviceid" , "deviceid" , "nuid"})
public class CloseDeviceAction extends SetSwitchDeviceStatusAction {
	
	@Override
	public String execute() 
	{
		super.setStatus(0);
		return super.execute();
	}

}
