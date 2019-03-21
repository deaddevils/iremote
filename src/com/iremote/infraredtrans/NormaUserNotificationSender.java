package com.iremote.infraredtrans;

import java.util.List;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.ZWaveDeviceService;

public class NormaUserNotificationSender implements INotificationSender {

	@Override
	public void pushmessage(Notification n, PhoneUser phoneuser,String devicename) 
	{
		if ( phoneuser == null )
			return ;
		NotificationHelper.pushmessage(n, phoneuser.getPhoneuserid(), devicename);
	}

	@Override
	public void pushmovementmessage(Notification n, PhoneUser phoneuser,String devicename) 
	{
		pushmessage(n , phoneuser ,devicename);
	}

	@Override
	public void triggeralarm(PhoneUser phoneuser, Notification notification) 
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.queryAlarmDevice(notification.getDeviceid());
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( ZWaveDevice d : lst )
		{
			CommandTlv ct = CommandUtil.createAlarmCommand(d.getNuid());
			SynchronizeRequestHelper.synchronizeRequest(d.getDeviceid(), ct , 0);
		}
	}


}
