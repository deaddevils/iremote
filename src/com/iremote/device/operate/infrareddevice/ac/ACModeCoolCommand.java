package com.iremote.device.operate.infrareddevice.ac;

public class ACModeCoolCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{
		command[10] = 2;
		command[9] = 2 ;
	}

}
