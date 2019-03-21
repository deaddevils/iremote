package com.iremote.task.notification;

import java.util.List;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.UserInOutService;
import com.iremote.service.ZWaveDeviceService;

public class GooutWarning implements Runnable {

	private PhoneUser user;
	private List<String> deviceid ;
	
	public GooutWarning(PhoneUser user, List<String> deviceid) {
		super();
		this.user = user;
		this.deviceid = deviceid;
	}

	@Override
	public void run() 
	{
		if ( deviceid == null || deviceid.size() == 0 || user == null )
			return ;
		
		UserInOutService uis = new UserInOutService();
		List<String> uid = uis.queryUserInhomeDevice(user.getPhoneuserid() , deviceid);
		
		if ( uid != null )
			deviceid.removeAll(uid);
		
		if ( deviceid.size() == 0 )
			return ;
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> zdl = zds.queryOpeningDoorSensor(deviceid);
		
		if ( zdl == null || zdl.size() == 0 )
			return ;
		
		StringBuffer devicename = new StringBuffer();
		 
		for ( ZWaveDevice zd : zdl)
		{
			devicename.append(zd.getName());
			devicename.append(",");
		}
		
		NotificationHelper.pushGooutMessage(user, devicename.substring(0,devicename.length() -1));
		
	}
	
}
