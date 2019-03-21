package com.iremote.infraredtrans.zwavecommand;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;

public class DoorSensorNotificationReportProcessor extends NotificationReportProcessor
{
	private int status;
	
	@Override
	protected void parseReport()
	{
		super.parseReport();
		if ( notificationtype == 0x06)  // Access Control
		{
			if ( notificationevent == 0x16 ) //Window/Door is open
			{
				message = IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN;
				status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_OPEN;
			}
			else if ( notificationevent == 0x17 ) //Window/Door is closed
			{
				message = IRemoteConstantDefine.WARNING_TYPE_DOOR_CLOSE;
				status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_CLOSE;
			}
		}
	}
	
	@Override
	protected void updateDeviceStatus()
	{
		if ( notificationtype == 0x06)  // Access Control
		{
			if ( notificationevent == 0x16 ) //Window/Door is open
			{
				zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_OPEN);
				zrb.getDevice().setShadowstatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_OPEN);
				if (!zdah.hasArmedByUserSetting())
					return;
				if (!zdah.hasSetDelayAlarm())
					super.appendWarningstatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_OPEN);
				else
					createTimerTask(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zrb, IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN_DELAY_WARNING,
							IRemoteConstantDefine.DEVICE_STATUS_DOOR_OPEN);
			}
			else if ( notificationevent == 0x17 ) //Window/Door is closed
			{
				zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_CLOSE);
				zrb.getDevice().setShadowstatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_CLOSE);
			}
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
		if ( StringUtils.isBlank(message))
			return null ;
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , status);
	}
}
