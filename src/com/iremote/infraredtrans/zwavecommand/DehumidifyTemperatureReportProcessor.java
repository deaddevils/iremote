package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class DehumidifyTemperatureReportProcessor extends ZWaveReportBaseProcessor
{

	public DehumidifyTemperatureReportProcessor()
	{
		super();
		this.dontsavenotification();
	}
	
	@Override
	protected void updateDeviceStatus()
	{
		zrb.getDevice().setStatuses(Utils.setJsonArrayValue(zrb.getDevice().getStatuses(), 8, zrb.getCommandvalue().getValue()));
		
	}

	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
