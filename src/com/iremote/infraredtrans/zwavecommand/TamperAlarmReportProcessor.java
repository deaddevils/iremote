package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;

public class TamperAlarmReportProcessor extends ZWaveReportBaseProcessor {

	private int status;
	protected void updateDeviceStatus()
	{
		status = zrb.getCmd()[7] & 0xff ;
		if ( status == 3 )
		{
			zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM);
			super.appendWarningstatus(IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM);
		}
	}


	@Override
	public String getMessagetype() {
		if ( status == 3 )
			return IRemoteConstantDefine.WARNING_TYPE_TAMPLE;
		return null ;
	}
}
