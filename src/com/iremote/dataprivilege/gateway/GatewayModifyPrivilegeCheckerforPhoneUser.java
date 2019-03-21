package com.iremote.dataprivilege.gateway;

import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.RemoteService;

public class GatewayModifyPrivilegeCheckerforPhoneUser extends GatewayPrivilegeChecker<PhoneUser>
{
	@Override
	public boolean checkprivilege()
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if ( r == null )
			return true ;  //actions will return device not exists error. 
		else 
			return ( r.getPhoneuserid() != null && r.getPhoneuserid().equals(user.getPhoneuserid())); 
	}

}
