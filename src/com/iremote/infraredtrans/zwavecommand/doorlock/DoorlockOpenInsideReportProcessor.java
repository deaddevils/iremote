package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;

public class DoorlockOpenInsideReportProcessor extends DoorlockStatusReportBase
{
	@Override
	protected String getOperateorName()
	{
		return null;
	}

	@Override
	protected void updateDeviceStatus()
	{
		zrb.getDevice().setShadowstatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
		if ( !DoorlockReportHelper.isDoorlockWarning(zrb.getDevice()) )
			zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);

		if ( this.isUserArmed())
			super.appendWarningstatus(IRemoteConstantDefine.LOCK_OPEN_INSIDE);
	}

	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE;
	}

	@Override
	protected IZwaveReportCache createCacheReport()
	{
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
	}
}
