package com.iremote.infraredtrans.zwavecommand.doorlock;


import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class DoorlockPasswordErrorAlarmProcessor extends ZWaveReportBaseProcessor
{
	@Override
	protected void updateDeviceStatus() 
	{
		zrb.getDevice().setStatus(IRemoteConstantDefine.LOCK_PASSWORD_ERROR_5_TIMES);
		
		super.appendWarningstatus(IRemoteConstantDefine.LOCK_PASSWORD_ERROR_5_TIMES);
	}

	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES;
	}
}
