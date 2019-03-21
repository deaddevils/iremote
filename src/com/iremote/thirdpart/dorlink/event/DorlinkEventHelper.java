package com.iremote.thirdpart.dorlink.event;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Remote;
import com.iremote.domain.ThirdPart;
import com.iremote.service.RemoteService;
import com.iremote.service.ThirdPartService;

@Deprecated
public class DorlinkEventHelper
{
	private static Integer THIRDPART_DORLINK_ALL_ID = null ;
	private static Object synObject = new Object();

	public static int queryThirdpartid()
	{
		if ( THIRDPART_DORLINK_ALL_ID != null )
			return THIRDPART_DORLINK_ALL_ID;
		
		synchronized(synObject)
		{
			if ( THIRDPART_DORLINK_ALL_ID != null )
				return THIRDPART_DORLINK_ALL_ID;
			
			ThirdPartService tps = new ThirdPartService();
			ThirdPart tp = tps.query(IRemoteConstantDefine.THIRDPARTER_DORLINK_ALL);

			if ( tp == null )
				THIRDPART_DORLINK_ALL_ID = 0 ;
			else 
				THIRDPART_DORLINK_ALL_ID = tp.getThirdpartid();
			return THIRDPART_DORLINK_ALL_ID;
		}
	}
	
	public static boolean isDorlinkGateway(String deviceid)
	{
		RemoteService rs = new RemoteService() ;
		Remote r = rs.getIremotepassword(deviceid);
		
		return ( r.getPlatform() == IRemoteConstantDefine.PLATFORM_DORLINK );
	}
}
