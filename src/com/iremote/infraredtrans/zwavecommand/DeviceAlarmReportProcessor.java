package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;

public class DeviceAlarmReportProcessor extends ZWaveReportBaseProcessor 
{

	public DeviceAlarmReportProcessor() 
	{
		super();
		super.dontsavenotification();
	}

	protected void updateDeviceStatus()
	{
		int status = zrb.getCommandvalue().getValue() ;
		
		if ( status != 0xff && status != 0x00 )
			return; 
		zrb.getDevice().setStatus(status);
	}

	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
}
