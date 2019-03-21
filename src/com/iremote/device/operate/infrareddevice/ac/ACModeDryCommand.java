package com.iremote.device.operate.infrareddevice.ac;

public class ACModeDryCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{
		command[10] = 4;
		command[9] = 2 ;
	}

}
