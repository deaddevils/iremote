package com.iremote.event.pushmessage;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;

public class PushWarningMessage extends ZWaveDeviceEvent implements ITextMessageProcessor{

	@Override
	public void run() 
	{
		NotificationHelper.pushmessage(this);
	}
	
	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}


}
