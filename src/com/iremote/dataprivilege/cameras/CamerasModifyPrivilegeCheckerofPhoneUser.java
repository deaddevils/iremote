package com.iremote.dataprivilege.cameras;

import java.util.List;

import com.iremote.domain.Camera;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.CameraService;
import com.iremote.service.RemoteService;

public class CamerasModifyPrivilegeCheckerofPhoneUser extends CamerasPrivilegeChecker<PhoneUser>
{
	@Override
	public boolean checkprivilege()
	{
		CameraService cs = new CameraService();
		List<Camera> lst = cs.query(cameraids);
		
		if ( lst == null || lst.size() == 0 )
			return true;
		
		RemoteService rs = new RemoteService();
		
		for ( Camera c : lst )
		{
			Remote r = rs.getIremotepassword(c.getDeviceid());
			
			if ( r == null )
				continue;
			
			if ( r.getPhoneuserid() == null || !r.getPhoneuserid().equals(user.getPhoneuserid()) )
				return false;
		}
		return true;
	}

}
