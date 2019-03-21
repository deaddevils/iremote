package com.iremote.dataprivilege.zwavedevice;

import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;

public class ZwaveDeviceModifyPrivilegeCheckerforPhoneUser extends ZwaveDevicePrivilegeChecker<PhoneUser>
{

	@Override
	public boolean checkprivilege()
	{
		if ( this.zwavedeviceid == null )
			return true ;
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(zwavedeviceid);
		
		if ( zd == null )
			return true;
		
		RemoteService rs = new RemoteService();
		Integer oid = rs.queryOwnerId(zd.getDeviceid());
		if ( oid == null )
			return false ;
		
		return oid.equals(user.getPhoneuserid());
	}

}
