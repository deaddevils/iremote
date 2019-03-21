package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;

public class NotificationReportProcessor extends ZWaveReportBaseProcessor
{
	protected int notificationtype;
	protected int notificationstatus;
	protected int notificationevent;
	protected String message ; 

	@Override
	protected void parseReport()
	{
		notificationstatus = zrb.getCmd()[5] & 0xff;
		notificationtype = zrb.getCmd()[6] & 0xff;
		notificationevent = zrb.getCmd()[7] & 0xff;
	}

	@Override
	protected void updateDeviceStatus()
	{
		if ( notificationtype == 0x08  // Power Management
				&& notificationevent == 0x08)  //Over-current detected
		{
			super.appendWarningstatus(IRemoteConstantDefine.POWER_OVER_LOAD);
			message = IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD;
		}
	}

	@Override
	public String getMessagetype()
	{
		return message;
	}

}
