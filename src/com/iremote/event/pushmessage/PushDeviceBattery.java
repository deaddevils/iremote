package com.iremote.event.pushmessage;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.Utils;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;

public class PushDeviceBattery extends ZWaveDeviceEvent implements ITextMessageProcessor{
	
	@Override
	public void run() 
	{
		setWarningstatus(Utils.getJsonArrayLastValue(getWarningstatuses()));
		NotificationHelper.pushBatteryStatus(this , getEventtime());
	}

	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}

}
