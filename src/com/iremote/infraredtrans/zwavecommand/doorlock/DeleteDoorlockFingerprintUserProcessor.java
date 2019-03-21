package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.common.IRemoteConstantDefine;

public class DeleteDoorlockFingerprintUserProcessor extends DeleteDoorlockPasswordUserProcessor
{
	@Override
	protected int getUserType()
	{
		return IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_FINGERPRINT;
	}
}
