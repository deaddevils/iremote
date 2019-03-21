package com.iremote.thirdpart.wcj.action;

import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid", "lockid"})
public class SetAdminPassword extends SetTempPassword {

	@Override
	protected byte getCommandUsercode()
	{
		return (byte)0xF0;
	}

}
