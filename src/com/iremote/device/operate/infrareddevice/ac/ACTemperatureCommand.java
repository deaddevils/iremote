package com.iremote.device.operate.infrareddevice.ac;

public class ACTemperatureCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{	
		command[4] = value ;
		command[9] = 6 ;
	}

}
