package com.iremote.device.operate.infrareddevice.ac;

public class ACTemperatureIncreaseCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{	
		if ( command[4] < 30 ) 
			command[4] ++ ;
		command[9] = 6 ;
	}

}
