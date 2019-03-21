package com.iremote.infraredtrans.zwavecommand.doorlock;


import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.asynctosync.AsynctosyncManager;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

@Deprecated
public class PasswordTimeSpanReportProcessor extends ZWaveReportBaseProcessor
{	
	
	public PasswordTimeSpanReportProcessor() {
		super();
		super.dontpusmessage();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		String key =  String.format("%s_%d", IRemoteConstantDefine.SYNC_KEY_SETTIMESPAN , zrb.getDevice().getZwavedeviceid() );
		AsynctosyncManager.getInstance().onResponse(key, zrb.getCmd());
	}

	@Override
	public String getMessagetype() {
		return null;
	}
	
}