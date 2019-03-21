package com.iremote.device.operate.infrareddevice.ac;

public class ACModeCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{
		command[10] = value;
		command[9] = 2 ;
	}

}
