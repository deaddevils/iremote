package com.iremote.device.operate.zwavedevice;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class MultilevelSwitchOpenCommand extends ZwaveDeviceOperateCommandBase{

	@Override
	public CommandTlv createCommand() 
	{
		status = 99 ;
		return CommandUtil.createMultilevelSwitchCommand(zwavedevice.getNuid(), status);
	}

}
