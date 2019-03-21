package com.iremote.device.operate.zwavedevice;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class AirConditionOnCommand extends ZwaveDeviceOperateCommandBase {

	@Override
	public CommandTlv createCommand() 
	{
		status = 5 ;
		return CommandUtil.createAirconditionCommand(zwavedevice.getNuid(), status);
	}

}
