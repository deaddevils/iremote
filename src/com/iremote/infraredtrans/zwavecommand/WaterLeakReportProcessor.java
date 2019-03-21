package com.iremote.infraredtrans.zwavecommand;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;

public class WaterLeakReportProcessor extends ZWaveReportBaseProcessor 
{
	private String message = IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK;
	
	@Override
	protected void updateDeviceStatus()
	{
		zrb.getDevice().setShadowstatus(0xff);
		zrb.getDevice().setStatus(0xff);
		if ( DeviceHelper.isDeviceArm(zrb.getDevice()))
			super.appendWarningstatus(0xff);
		else 
			message = IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

	@Override
	public String getMessagetype()
	{
		if (warningstatus == null) {
			return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
		}
		return message;
	}

}
