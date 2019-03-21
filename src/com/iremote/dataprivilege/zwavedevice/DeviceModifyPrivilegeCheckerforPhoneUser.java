package com.iremote.dataprivilege.zwavedevice;

import org.apache.commons.lang.StringUtils;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.domain.PhoneUser;
import com.iremote.service.ZWaveDeviceService;

public class DeviceModifyPrivilegeCheckerforPhoneUser extends DeviceOperationPrivilegeChecker<PhoneUser>
{
	@Override
	public boolean checkprivilege()
	{
		if ( zwavedeviceid == 0 && nuid == 0 && StringUtils.isNotBlank(deviceid))
			return PhoneUserHelper.checkModifyPrivilege(user, deviceid);
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		if ( zwavedeviceid != 0 )
		{
			device = zds.query(zwavedeviceid);
				
			if ( device == null )  // action will return 'device not exists error'
				return true ;
			
			super.deviceid = device.getDeviceid();
		}
		else if ( StringUtils.isBlank(deviceid)) // action will return 'device not exists error'
			return true;
		
		return PhoneUserHelper.checkModifyPrivilege(user, deviceid);
	}
}
