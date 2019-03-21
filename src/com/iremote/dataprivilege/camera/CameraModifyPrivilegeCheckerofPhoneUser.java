package com.iremote.dataprivilege.camera;

import com.iremote.domain.Camera;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.CameraService;
import com.iremote.service.RemoteService;

public class CameraModifyPrivilegeCheckerofPhoneUser extends CameraPrivilegeChecker<PhoneUser>
{
	@Override
	public boolean checkprivilege()
	{
		CameraService cs = new CameraService();
		Camera c = cs.query(cameraid);
		
		if ( c == null )
			return true;
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(c.getDeviceid());
		
		if ( r == null )
			return true;
		
		if ( r.getPhoneuserid() == null || !r.getPhoneuserid().equals(user.getPhoneuserid()) )
			return false;
		
		return true;
	}

}
