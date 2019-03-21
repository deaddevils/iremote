package com.iremote.infraredtrans.zwavecommand.bgm;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class BGMVolumnReportProcessor extends ZWaveReportBaseProcessor
{
	public BGMVolumnReportProcessor() 
	{
		super();
		super.dontsavenotification();
	}
	
	@Override
	protected void updateDeviceStatus()
	{
		if ( zrb.getCmd() == null || zrb.getCmd().length < 3)
			return ;
		
		int v = zrb.getCmd()[2] & 0xff;
		zrb.getDevice().setStatuses(Utils.setJsonArrayValue(zrb.getDevice().getStatuses(), 3, v));
	}

	@Override
	public String getMessagetype() 
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
