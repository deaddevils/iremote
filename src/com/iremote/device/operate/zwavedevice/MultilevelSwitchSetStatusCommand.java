package com.iremote.device.operate.zwavedevice;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class MultilevelSwitchSetStatusCommand extends ZwaveDeviceOperateCommandBase{

	@Override
	public CommandTlv createCommand() 
	{
		return CommandUtil.createMultilevelSwitchCommand(zwavedevice.getNuid(), status);
	}

}
