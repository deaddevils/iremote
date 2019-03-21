package com.iremote.device.operate.zwavedevice;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class LockLockCommad extends ZwaveDeviceOperateCommandBase{

	@Override
	public CommandTlv createCommand() 
	{
		return CommandUtil.createLockCommand(zwavedevice.getNuid(), (byte)255);
	}

}
