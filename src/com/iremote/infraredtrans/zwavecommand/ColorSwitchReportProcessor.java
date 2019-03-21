package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class ColorSwitchReportProcessor extends ZWaveReportBaseProcessor
{

	public ColorSwitchReportProcessor() {
		super();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		int value = zrb.getCmd()[2] & 0xff;
		if ( value == 255 )
			super.zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_SWITCH_OPEN);
		else if ( value == 0 )
			super.zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_SWITCH_CLOSE);
		else 
		{
			super.zrb.getDevice().setStatuses(Utils.setJsonArrayValue(zrb.getDevice().getStatuses(), 1, value * 255 / 100));
		}
	}

	@Override
	public String getMessagetype() 
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
