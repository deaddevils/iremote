package com.iremote.event.camera;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.CameraEvent;

public class PushCameraWarningMessage extends CameraEvent implements ITextMessageProcessor
{
	@Override
	public void run() 
	{
		if ( getWarningstatus() == null || getWarningstatus() == 0 )
			NotificationHelper.pushmessage(this);
		else 
			NotificationHelper.pushWarningNotification(this, getName());
	}

	@Override
	public String getTaskKey() 
	{
		return super.getDeviceid();
	}

}
