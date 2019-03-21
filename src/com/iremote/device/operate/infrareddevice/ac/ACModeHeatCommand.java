package com.iremote.device.operate.infrareddevice.ac;

public class ACModeHeatCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{
		command[10] = 5;
		command[9] = 2 ;
	}

}
