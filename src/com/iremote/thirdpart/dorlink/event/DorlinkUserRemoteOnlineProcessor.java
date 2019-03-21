package com.iremote.thirdpart.dorlink.event;

import com.iremote.thirdpart.rentinghouse.event.RemoteOnlineProcessor;

@Deprecated
public class DorlinkUserRemoteOnlineProcessor extends RemoteOnlineProcessor
{
	@Override
	protected Integer queryThirdpartid()
	{
		if ( !DorlinkEventHelper.isDorlinkGateway(super.getDeviceid()))
			return 0;

		return DorlinkEventHelper.queryThirdpartid();
	}
}
