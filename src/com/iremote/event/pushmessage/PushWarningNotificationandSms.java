package com.iremote.event.pushmessage;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;

public class PushWarningNotificationandSms extends ZWaveDeviceEvent implements ITextMessageProcessor{

	
	@Override
	public void run() 
	{
		if ( getWarningstatus() == null || getWarningstatus() == 0 )
			return ;
		NotificationHelper.pushWarningNotification(this, getName());
	}


	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}
	

}
