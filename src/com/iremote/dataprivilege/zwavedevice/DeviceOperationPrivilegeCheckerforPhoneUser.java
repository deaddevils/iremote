package com.iremote.dataprivilege.zwavedevice;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

public class DeviceOperationPrivilegeCheckerforPhoneUser extends DeviceOperationPrivilegeChecker<PhoneUser>
{
	@Override
	public boolean checkprivilege()
	{
		if ( zwavedeviceid == 0 && nuid == 0 && StringUtils.isNotBlank(deviceid))
		{
			if( PhoneUserHelper.checkPrivilege(user, deviceid) == true )
				return true;
		ZWaveDeviceService zds = new ZWaveDeviceService();
			List<ZWaveDevice> lst = zds.querybydeviceid(deviceid);
			if ( lst == null || lst.size() != 1 )
				return false ;
			device = lst.get(0);
			return PhoneUserHelper.checkPrivilege(user, device);
		}
		else 
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			
			if ( zwavedeviceid != 0 )
				device = zds.query(zwavedeviceid);
			else if ( nuid != 0 && StringUtils.isNotBlank(deviceid) )
				device = zds.querybydeviceid(deviceid, nuid);
			
			if ( device == null )  // action will return 'device not exists error'
				return true ;
			
			return PhoneUserHelper.checkPrivilege(user, device);
		}
	}

}
