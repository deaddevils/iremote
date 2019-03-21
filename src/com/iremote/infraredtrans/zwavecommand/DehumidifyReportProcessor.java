package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class DehumidifyReportProcessor extends ZWaveReportBaseProcessor
{
	
	public DehumidifyReportProcessor()
	{
		super();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		int channelid = zrb.getCommandvalue().getChannelid();
		
		if ( channelid == 1 )
		{
			zrb.getDevice().setStatus(zrb.getCommandvalue().getValue());
			zrb.getCommandvalue().setChannelid(0);
		}
		else 
			zrb.getDevice().setStatuses(Utils.setJsonArrayValue(zrb.getDevice().getStatuses(), channelid - 1, zrb.getCommandvalue().getValue()));
	}

	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
