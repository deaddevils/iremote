package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;

public class MultiLevelSwitchReportProcessor extends ZWaveReportBaseProcessor {

	public MultiLevelSwitchReportProcessor() {
		super();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		int status = zrb.getCommandvalue().getValue() ;
		
		if ( status > 99 || status < 0 )
			return;
		
		zrb.getDevice().setStatus(status);
		zrb.getDevice().setShadowstatus(status);
	}

	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
