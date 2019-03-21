package com.iremote.thirdpart.rentinghouse.event;

public class ZWaveDeviceWarningProcessor extends ZWaveDeviceEventProcessor {

	@Override
	public void run() {
		if ( getWarningstatus() == null )
			return ;
		super.run();
	}

}
