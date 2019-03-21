package com.iremote.infraredtrans.zwavecommand.doorlock;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.asynctosync.AsynctosyncManager;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

@Deprecated
public class SetPasswordReportProcessor extends ZWaveReportBaseProcessor
{	
	private static Log log = LogFactory.getLog(SetPasswordReportProcessor.class);
	
	public SetPasswordReportProcessor() {
		super();
		super.dontpusmessage();
		super.dontsavenotification();
	}


	@Override
	protected void updateDeviceStatus()
	{
		String key =  String.format("%s_%d", IRemoteConstantDefine.SYNC_KEY_SETPASSWORD , zrb.getDevice().getZwavedeviceid() );
		
		if ( log.isInfoEnabled())
			log.info(String.format("Report arrive , key = %s", key));
		
		AsynctosyncManager.getInstance().onResponse(key, zrb.getCmd());
	}


	@Override
	public String getMessagetype() {
		return null;
	}
}
