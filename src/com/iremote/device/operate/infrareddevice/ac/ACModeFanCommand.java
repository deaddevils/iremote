package com.iremote.device.operate.infrareddevice.ac;

public class ACModeFanCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{
		command[10] = 2;
		command[9] = 2 ;
	}

}
