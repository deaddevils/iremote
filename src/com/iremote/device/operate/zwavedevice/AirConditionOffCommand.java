package com.iremote.device.operate.zwavedevice;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class AirConditionOffCommand extends ZwaveDeviceOperateCommandBase {

	@Override
	public CommandTlv createCommand() 
	{
		status = 0 ;
		return CommandUtil.createAirconditionCommand(zwavedevice.getNuid(), status);
	}

}
