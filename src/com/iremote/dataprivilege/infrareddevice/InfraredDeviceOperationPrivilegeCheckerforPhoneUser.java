package com.iremote.dataprivilege.infrareddevice;

import com.iremote.domain.PhoneUser;

public class InfraredDeviceOperationPrivilegeCheckerforPhoneUser extends InfraredDevicePrivilegeChecker<PhoneUser>
{

	@Override
	public boolean checkprivilege()
	{
		return false;
	}

}
