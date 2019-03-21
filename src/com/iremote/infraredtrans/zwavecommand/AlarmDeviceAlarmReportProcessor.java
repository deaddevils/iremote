package com.iremote.infraredtrans.zwavecommand;


public class AlarmDeviceAlarmReportProcessor extends DeviceAlarmReportProcessor
{

	@Override
	protected void updateDeviceStatus()
	{
		super.updateDeviceStatus();

		if (  zrb.getDevice().getStatus() == 0xff )
			super.appendWarningstatus(0xff);
		else if ( zrb.getDevice().getStatus() == 0x00 )
			zrb.getDevice().setWarningstatuses(null);
	}

}
