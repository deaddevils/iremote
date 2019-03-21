package com.iremote.device.operate.infrareddevice.ac;

public class ACTemperatureDecreaseCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{	
		if ( command[4] > 19 ) 
			command[4] -- ;
		command[9] = 6 ;
	}

}
