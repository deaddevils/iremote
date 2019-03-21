package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

//command: 0x33 0xA4 0x05 0x00 0xx0 0x01 0xx1 0x02 0xx2 0x03 0xx3 0x04 0xx4
public class ColorSwitchSelfdefineReportProcessor extends ZWaveReportBaseProcessor
{

	public ColorSwitchSelfdefineReportProcessor() {
		super();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		if ( super.zrb.getCmd() == null || super.zrb.getCmd().length < 4)
			return ;
		int in = zrb.getCmd()[2];
		
		String js = zrb.getDevice().getStatuses();
		for ( int i = 0 ; i < in ; i ++ )
		{
			js = Utils.setJsonArrayValue(js, zrb.getCmd()[3 + i * 2], (zrb.getCmd()[3 + i * 2 + 1] & 0xff ));
		}
		zrb.getDevice().setStatuses(js);
	}

	@Override
	public String getMessagetype() 
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
