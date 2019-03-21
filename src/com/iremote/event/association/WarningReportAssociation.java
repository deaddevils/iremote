package com.iremote.event.association;

public class WarningReportAssociation extends DeviceReportAssociation{

	@Override
	public void run() 
	{
		if ( getWarningstatus() == null || getWarningstatus() == 0 )
			return ;
		super.run();
	}

	
}
