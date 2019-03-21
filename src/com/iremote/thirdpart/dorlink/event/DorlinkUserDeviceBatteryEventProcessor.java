package com.iremote.thirdpart.dorlink.event;

import com.iremote.thirdpart.rentinghouse.event.ZWaveDeviceBatteryEventProcessor;

@Deprecated
public class DorlinkUserDeviceBatteryEventProcessor extends ZWaveDeviceBatteryEventProcessor
{
	@Override
	protected Integer queryThirdpartid()
	{
		if ( !DorlinkEventHelper.isDorlinkGateway(super.getDeviceid()))
			return 0;

		return DorlinkEventHelper.queryThirdpartid();
	}
}
