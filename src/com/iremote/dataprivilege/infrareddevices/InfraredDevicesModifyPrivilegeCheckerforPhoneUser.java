package com.iremote.dataprivilege.infrareddevices;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.Utils;
import com.iremote.dataprivilege.PhoneUserDataPrivilegeCheckor;
import com.iremote.domain.PhoneUser;

public class InfraredDevicesModifyPrivilegeCheckerforPhoneUser extends InfraredDevicesModifyPrivilegeChecker<PhoneUser>
{
	@Override
	public boolean checkprivilege()
	{
		PhoneUserDataPrivilegeCheckor checkor = new PhoneUserDataPrivilegeCheckor(user);

		if ( StringUtils.isNotBlank(infrareddeviceids))
		{
			List<Integer> il = Utils.jsontoIntList(infrareddeviceids);
			for ( Integer iid : il )
				if ( checkor.checkInfraredDeviceModifyPrivilege(iid) == false )
					return false ;
		}
		
		return true;
	}
}
