package com.iremote.thirdpart.dorlink.event;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.thirdpart.rentinghouse.event.RemoteProcessor;

@Deprecated
public class DorlinkUserDeleteRemoteProcessor extends RemoteProcessor
{

	@Override
	protected String getType() 
	{
		return IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE;
	}
	
	@Override
	protected Integer queryThirdpartid()
	{
		if ( !DorlinkEventHelper.isDorlinkGateway(super.getDeviceid()))
			return 0;

		return DorlinkEventHelper.queryThirdpartid();
	}

}
