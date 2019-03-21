package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;

public class SOSAlarmCentralSceneReportProcessor extends ZWaveReportBaseProcessor{

	public SOSAlarmCentralSceneReportProcessor() {
		super();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		int status = zrb.getCmd()[4] & 0xff ;
		zrb.getDevice().setStatus(status);
		zrb.getDevice().setShadowstatus(status);
		super.oldstatus = 0 ; //do not care old status on scene panel was triggerd .
	}

	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
