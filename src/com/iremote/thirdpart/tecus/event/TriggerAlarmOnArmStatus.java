package com.iremote.thirdpart.tecus.event;

public class TriggerAlarmOnArmStatus extends TriggerAlarmProcessor{

	@Override
	public void run() {
		
		if ( getWarningstatus() != null && getWarningstatus() != 0 )
			super.run();
	}
	

}
