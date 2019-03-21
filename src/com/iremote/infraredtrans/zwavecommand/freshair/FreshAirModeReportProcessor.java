package com.iremote.infraredtrans.zwavecommand.freshair;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class FreshAirModeReportProcessor extends ZWaveReportBaseProcessor
{

	public FreshAirModeReportProcessor()
	{
		super();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		int status = zrb.getCommandvalue().getValue();
		
		if ( status == 0 )
			this.zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_SWITCH_CLOSE);
		else 
		{
			this.zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_SWITCH_OPEN);
			if ( status != 5 )
				this.zrb.getDevice().setStatuses(Utils.setJsonArrayValue(zrb.getDevice().getStatuses(), 0, status));
		}  
	}

	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
