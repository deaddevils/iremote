package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;

public class WaterLeakRestoreReportProcessor extends ZWaveReportBaseProcessor 
{

	@Override
	protected void updateDeviceStatus()
	{
		zrb.getDevice().setShadowstatus(0);
		zrb.getDevice().setStatus(0);
	}

	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
