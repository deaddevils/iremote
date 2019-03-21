package com.iremote.thirdpart.wcj.action;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY , domain = "device", parameters = {"zwavedeviceid", "lockid"})
public class SetUserPassword extends SetTempPassword {
	
	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}

	public int getUsercode()
	{
		return usercode;
	}
	
	@Override
	protected byte getCommandUsercode() 
	{	
		if ( StringUtils.isNotBlank(super.lock.getProductor()) 
				&& super.lock.getProductor().startsWith(IRemoteConstantDefine.JWZH_ZWAVE_PRODUCTOR) 
				&& StringUtils.isNotBlank(super.lock.getFunctionversion()))
			usercode = 0xff;
		return (byte)usercode;
	}

	
}
