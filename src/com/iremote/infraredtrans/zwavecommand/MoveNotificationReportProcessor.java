package com.iremote.infraredtrans.zwavecommand;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;

public class MoveNotificationReportProcessor extends NotificationReportProcessor
{
	private int status;
	
	@Override
	protected void parseReport()
	{
		super.parseReport();
		if ( notificationtype == 0x07)  // Home Security
		{
			if ( notificationevent == 0x08 ) //Motion Detection
			{
				status = IRemoteConstantDefine.DEVICE_STATUS_MOVE_IN;
				message = IRemoteConstantDefine.WARNING_TYPE_MOVEIN;
			}
			else if ( notificationevent == 0x00 )
			{
				status = IRemoteConstantDefine.DEVICE_STATUS_MOVE_OUT;
				message = IRemoteConstantDefine.WARNING_TYPE_MOVEOUT;
			}
		}
	}
	
	@Override
	protected void updateDeviceStatus()
	{
		if ( notificationtype == 0x07)  // Home Security
		{
			if ( notificationevent == 0x08 ) //Motion Detection
			{
				zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_MOVE_IN);
				if (!zdah.hasArmedByUserSetting())
					return;
				if (!zdah.hasSetDelayAlarm())
					super.appendWarningstatus(IRemoteConstantDefine.DEVICE_STATUS_MOVE_IN);
				else
					createTimerTask(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zrb,
							IRemoteConstantDefine.WARNING_TYPE_MOVE_IN_DELAY_WARNING, IRemoteConstantDefine.DEVICE_STATUS_MOVE_IN);
			}
			else if ( notificationevent == 0x00 )
				zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_MOVE_OUT);
		}
	}
	
	protected void afterprocess()
	{
		if ( this.oldstatus == null || oldstatus != zrb.getDevice().getStatus() )
			this.notification.setEclipseby(0); 
	}

	@Override
	public String getMessagetype() 
	{
		return message ;
	}

	@Override
	protected IZwaveReportCache createCacheReport()
	{
		if ( StringUtils.isBlank(message))
			return null ;
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , status);
	}

}
