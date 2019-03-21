package com.iremote.dataprivilege.gateway;

import com.iremote.domain.ThirdPart;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;

public class GatewayModifyPrivilegeCheckerforThirdpart extends GatewayPrivilegeChecker<ThirdPart>
{
	@Override
	public boolean checkprivilege()
	{
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote cr = crs.querybyDeviceid(deviceid);
		if ( cr == null || cr.getThirdpartid() != user.getThirdpartid() )
			return false;
		
		return true ;  
	}

}
