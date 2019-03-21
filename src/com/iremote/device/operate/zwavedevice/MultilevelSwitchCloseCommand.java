package com.iremote.device.operate.zwavedevice;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class MultilevelSwitchCloseCommand extends ZwaveDeviceOperateCommandBase{

	@Override
	public CommandTlv createCommand() 
	{
		status = 0 ;
		return CommandUtil.createMultilevelSwitchCommand(zwavedevice.getNuid(), status);
	}

}
