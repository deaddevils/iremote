package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class SOSAlarmReportProcessor extends ZWaveReportBaseProcessor{

	@Override
	protected void updateDeviceStatus() 
	{
		if ( (zrb.getCmd()[5] & 0xff ) == 0xff && (zrb.getCmd()[6] & 0xff) == 0x0A )
		{
			zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_ALARM_ALARM);
			this.warningstatus = IRemoteConstantDefine.DEVICE_STATUS_ALARM_ALARM;// SOS message will never be eclipse 
			if ( !Utils.isJsonArrayContaints(zrb.getDevice().getWarningstatuses(), IRemoteConstantDefine.DEVICE_STATUS_ALARM_ALARM))
				zrb.getDevice().setWarningstatuses(Utils.jsonArrayAppend(zrb.getDevice().getWarningstatuses(), IRemoteConstantDefine.DEVICE_STATUS_ALARM_ALARM));
		}
	}

	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_SOS;
	}
	
	
	protected void savenotification()
	{
		super.savenotification();
		super.notification.setEclipseby(0); // SOS message will never be eclipse 
	}

}
