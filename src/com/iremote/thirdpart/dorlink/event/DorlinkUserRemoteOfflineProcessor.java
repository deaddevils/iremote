package com.iremote.thirdpart.dorlink.event;

import com.iremote.thirdpart.rentinghouse.event.RemoteOfflineProcessor;

@Deprecated
public class DorlinkUserRemoteOfflineProcessor extends RemoteOfflineProcessor
{
	@Override
	protected Integer queryThirdpartid()
	{
		if ( !DorlinkEventHelper.isDorlinkGateway(super.getDeviceid()))
			return 0;

		return DorlinkEventHelper.queryThirdpartid();
	}
}
