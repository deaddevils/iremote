package com.iremote.infraredtrans.zwavecommand.freshair;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class FreshAirFanModeReportProcessor extends ZWaveReportBaseProcessor
{

	public FreshAirFanModeReportProcessor()
	{
		super();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		int channelid = zrb.getCommandvalue().getChannelid();
		zrb.getDevice().setStatuses(Utils.setJsonArrayValue(zrb.getDevice().getStatuses(), channelid - 1, zrb.getCommandvalue().getValue()));
	}

	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
