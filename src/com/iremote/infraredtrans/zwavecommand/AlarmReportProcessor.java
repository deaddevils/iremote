package com.iremote.infraredtrans.zwavecommand;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;

public class AlarmReportProcessor extends ZWaveReportBaseProcessor 
{
	protected void updateDeviceStatus()
	{
		int type = zrb.getCommandvalue().getType();
		if ( type == 0x00 )
			processTamperAlarm();
		else if ( type == 0x01 || type == 0x02 || type == 0x05 )
			processAlarm(type);
	}
	
	private void processTamperAlarm()
	{
		int alarm = zrb.getCommandvalue().getValue() ;
		if ( alarm == 0xff )
		{
			zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM);
			if (DeviceHelper.isDeviceArm(zrb.getDevice()))
				super.appendWarningstatus(IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM);
		}
	}
	
	private void processAlarm(int type)
	{
		int status = zrb.getCommandvalue().getValue() ;
		
		if ( status == 0xff )
		{
			zrb.getDevice().setShadowstatus(0xff);
			zrb.getDevice().setStatus(0xff);
			if (DeviceHelper.isDeviceArm(zrb.getDevice()))
				super.appendWarningstatus(0xff);
		} else if (status == 0x00) {
			zrb.getDevice().setShadowstatus(0);
			zrb.getDevice().setStatus(0);
		}
	}

	@Override
	public String getMessagetype() {
		if ( super.warningstatus != null )
			return IRemoteConstantDefine.DEVICE_WARNING_MESSAGE_MAP.get(String.format("%s_%d", zrb.getDevice().getDevicetype() , super.warningstatus));
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
}
