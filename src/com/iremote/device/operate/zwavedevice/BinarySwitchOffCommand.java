package com.iremote.device.operate.zwavedevice;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class BinarySwitchOffCommand extends ZwaveDeviceOperateCommandBase {

	@Override
	public CommandTlv createCommand() 
	{
		byte status = 0 ;
		if ( channel == 0 )
			return CommandUtil.createSwitchCommand(zwavedevice.getNuid(), status);
		else 
			return CommandUtil.createSwitchCommand(zwavedevice.getNuid(), channel, status);
	}

}
