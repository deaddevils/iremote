package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;

public class DoorSensorReportProcessor extends ZWaveReportBaseProcessor
{
	private int status;
	private String message ;

	@Override
	protected void parseReport()
	{
		status = zrb.getCommandvalue().getValue() ;
		if ( status == 0xff )
			message = IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN;
		else if ( status == 0 )
			message = IRemoteConstantDefine.WARNING_TYPE_DOOR_CLOSE;
	}

	@Override
	protected void updateDeviceStatus()
	{
		zrb.getDevice().setShadowstatus(status);
		if ( zrb.getDevice().getStatus() == null ||  zrb.getDevice().getStatus() != IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM )
			zrb.getDevice().setStatus(status);

		if ( status == 0xff)
		{
			if (!zdah.hasArmedByUserSetting())
				return;
			if (!zdah.hasSetDelayAlarm())
				super.appendWarningstatus(status);
			else
                createTimerTask(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zrb,
						IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN_DELAY_WARNING, status);
		}
	}

    protected void afterprocess()
	{
		if ( oldshadowstatus == null || oldshadowstatus != zrb.getDevice().getShadowstatus() )
			this.notification.setEclipseby(0);
	}

	@Override
	public String getMessagetype()
	{
		return message;
	}

	@Override
	protected IZwaveReportCache createCacheReport()
	{
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , status);
	}

}
