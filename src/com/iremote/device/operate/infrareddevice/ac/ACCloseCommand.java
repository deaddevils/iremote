package com.iremote.device.operate.infrareddevice.ac;

public class ACCloseCommand extends ACOperateCommandBase {

	@Override
	protected void adjustCommand() 
	{	
		command[8] = 0 ;
		command[9] = 1 ;
	}

}
