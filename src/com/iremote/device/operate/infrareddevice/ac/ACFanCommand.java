package com.iremote.device.operate.infrareddevice.ac;

public class ACFanCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{
		command[5] = 1 ;
		command[9] = 3 ;
	}

}
