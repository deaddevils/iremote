package com.iremote.dataprivilege.zwavedevice;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

public class ZwaveDeviceOperationPrivilegeCheckerforPhoneUser extends ZwaveDevicePrivilegeChecker<PhoneUser>
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
		
		return PhoneUserHelper.checkPrivilege(user, zd);
	}

}
