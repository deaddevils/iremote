package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;

public class AddDoorlockFingerprintUserProcessor extends AddDoorlockPasswordUserProcessor
{
	@Override
	protected int getUserType()
	{
		return IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_FINGERPRINT;
	}
	
	@Override
	protected String getUserName()
	{
		return String.format("%s%d",MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_FINGER_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser())) , usercode);
	}
}
