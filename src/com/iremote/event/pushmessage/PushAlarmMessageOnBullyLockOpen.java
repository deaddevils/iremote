package com.iremote.event.pushmessage;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;

public class PushAlarmMessageOnBullyLockOpen extends ZWaveDeviceEvent implements ITextMessageProcessor{

	
	@Override
	public void run() 
	{
		this.setEventtype(IRemoteConstantDefine.WARNING_TYPE_LOCK_BULLY);
		if ( this.getAppendmessage() != null && this.getAppendmessage().containsKey("usercode"))
			NotificationHelper.pushAlarmSmsOnBullyLockOpen(this, getName());
	}


	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}
	

}
