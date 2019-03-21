package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class DoorbellRingReportProcessor extends ZWaveReportBaseProcessor {

	private int status;
	
	protected void updateDeviceStatus()
	{
		status = zrb.getCommandvalue().getValue() ;
		
		if ( status != 0xff )
			return ;
	}

	@Override
	public String getMessagetype() {
		if ( status != 0xff )
			return null ;
		return IRemoteConstantDefine.WARNING_TYPE_DOOR_BELL_RING;
	}

	@Override
	protected void afterprocess() 
	{
		if ( status == 0xff && notification != null )
			NotificationHelper.pushDoorbellRingMessage(notification , zrb.getPhoneuser());
	}
}
