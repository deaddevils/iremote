package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;

public class DoorlockCloseReportProcessor extends DoorlockStatusReportBase
{
	private int usercode;
	private String username ;
	protected boolean isStandardLock = false;
	
	@Override
	protected void updateDeviceStatus() 
	{		
		initUsername();
		int status = 255 ;
		
		zrb.getDevice().setShadowstatus(status);
		if ( !DoorlockReportHelper.isDoorlockWarning(zrb.getDevice()) )
		{
			zrb.getDevice().setStatus(status);
		}
	}
	
	private void initUsername()
	{
		if ( (zrb.getCmd()[7] & 0xff) == 1 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_KEY_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
		else if ( (zrb.getCmd()[7] & 0xff) == 3 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_CARD_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
		else if ( (zrb.getCmd()[7] & 0xff) == 9 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_AUTO_CLOSE,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
		else if ( (zrb.getCmd()[7] & 0xff) == 5 && zrb.getCmd().length > 9 )
		{
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
			if (isStandardZwaveDoorLock()) {
				usercode = (zrb.getCmd()[3] & 0xff);
			} else {
				usercode = (zrb.getCmd()[9] & 0xff);
			}
			username += usercode ;
		}
		
	}

	@Override
	protected String getOperateorName() {
		return username;
	}

	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE;
	}

	@Override
	protected IZwaveReportCache createCacheReport()
	{
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , 255);
	}

	@Override
	protected boolean isDuplicate(IZwaveReportCache current)
	{
		isStandardLock = isStandardZwaveDoorLock();

		if (!isStandardLock) {
			return super.isDuplicate(current);
		} else {
			super.isDuplicate(current);
			return super.duplicated ;
		}
	}
}
