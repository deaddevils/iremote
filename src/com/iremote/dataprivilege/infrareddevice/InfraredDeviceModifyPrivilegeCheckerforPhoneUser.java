package com.iremote.dataprivilege.infrareddevice;

import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.RemoteService;

public class InfraredDeviceModifyPrivilegeCheckerforPhoneUser extends InfraredDevicePrivilegeChecker<PhoneUser>
{

	@Override
	public boolean checkprivilege()
	{
		if ( this.infrareddeviceid == null )
			return true ; 
		InfraredDeviceService ids = new InfraredDeviceService();
		InfraredDevice id = ids.query(infrareddeviceid);
		
		if ( id == null )
			return true ; //Actions will return device not exists error.
		
		RemoteService rs = new RemoteService();
		Integer oid = rs.queryOwnerId(id.getDeviceid());
		if ( oid == null )
			return false ;
		
		return oid.equals(user.getPhoneuserid());
	}

}
