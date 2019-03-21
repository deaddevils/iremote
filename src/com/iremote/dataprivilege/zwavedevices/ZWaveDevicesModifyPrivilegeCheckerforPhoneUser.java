package com.iremote.dataprivilege.zwavedevices;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.Utils;
import com.iremote.dataprivilege.PhoneUserDataPrivilegeCheckor;
import com.iremote.domain.PhoneUser;

public class ZWaveDevicesModifyPrivilegeCheckerforPhoneUser extends ZWaveDevicesModifyPrivilegeChecker<PhoneUser>
{
	@Override
	public boolean checkprivilege()
	{
		PhoneUserDataPrivilegeCheckor checkor = new PhoneUserDataPrivilegeCheckor(user);
		
		if ( StringUtils.isNotBlank(zwavedeviceids))
		{
			List<Integer> zl = Utils.jsontoIntList(zwavedeviceids);
			for ( Integer zid : zl )
				if ( checkor.checkZWaveDeviceModifyPrivilege(zid) == false )
					return false ;
		}
		
		return true;
	}
}
