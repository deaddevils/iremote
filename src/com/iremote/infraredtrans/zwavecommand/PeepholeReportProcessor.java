package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;

public class PeepholeReportProcessor extends ZWaveReportBaseProcessor 
{
	private int status ;
	
	public PeepholeReportProcessor() 
	{
		super();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		status = zrb.getCommandvalue().getValue() ;
		
		if ( status != 0xff )
			super.dontsavenotification();
		zrb.getDevice().setStatus(status);
	}

	@Override
	public String getMessagetype() 
	{
//		if ( status == 0 )
			return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
//		else if ( status == 0xff )
//			return IRemoteConstantDefine.WARNING_TYPE_PEEPHOLE_BELL_RING;
//		return null ;
	}

	@Override
	protected void afterprocess() 
	{
//		if ( status == 0xff )
//			NotificationHelper.pushPeepholebellRingMessage(zrb.getDevice() , notification.getReporttime() , zrb.getPhoneuser());
	}

}
