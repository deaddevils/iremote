package com.iremote.thirdpart.dorlink.event;

import com.iremote.thirdpart.rentinghouse.event.ZWaveDeviceEventProcessor;

@Deprecated
public class DorlinkUserDeviceEventProcessor extends ZWaveDeviceEventProcessor
{
	@Override
	protected Integer queryThirdpartid()
	{
		if ( !DorlinkEventHelper.isDorlinkGateway(super.getDeviceid()))
			return 0;

		return DorlinkEventHelper.queryThirdpartid();
	}
}
