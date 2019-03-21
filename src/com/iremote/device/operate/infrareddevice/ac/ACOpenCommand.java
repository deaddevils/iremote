package com.iremote.device.operate.infrareddevice.ac;

public class ACOpenCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{
		command[8] = 1 ;
		command[9] = 1 ;
	}

}
