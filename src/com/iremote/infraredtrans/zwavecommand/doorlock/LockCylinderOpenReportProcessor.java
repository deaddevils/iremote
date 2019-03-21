package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;

public class LockCylinderOpenReportProcessor extends DoorlockOpenReportProcessor
{
	@Override
	protected void initusercode()
	{
//		if ( (zrb.getCmd()[7] & 0xff) == 2 )
//			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_KEY_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
//		else if ( (zrb.getCmd()[7] & 0xff) == 4 )
//			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_CARD_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
//		else 
		
		if ( (zrb.getCmd()[7] & 0xff) == 6 )
		{
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
			usercode = (zrb.getCmd()[9] & 0xff);
			username += usercode ;
		}
	}
	
}
