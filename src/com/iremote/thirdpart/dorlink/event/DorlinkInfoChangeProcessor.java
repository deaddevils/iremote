package com.iremote.thirdpart.dorlink.event;

import com.iremote.thirdpart.rentinghouse.event.InfoChangeProcessor;

@Deprecated
public class DorlinkInfoChangeProcessor extends InfoChangeProcessor
{
	@Override
	protected Integer queryThirdpartid()
	{
		if ( !DorlinkEventHelper.isDorlinkGateway(super.getDeviceid()))
			return 0;

		return DorlinkEventHelper.queryThirdpartid();
	}
}
