package com.iremote.device.operate.infrareddevice.ac;

public class ACModeAutoCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{
		command[10] = 1;
		command[9] = 2 ;
	}

}
