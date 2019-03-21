package com.iremote.dataprivilege.camera;

import java.util.List;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.domain.Camera;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.CameraService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceShareService;

public class CameraOperationPrivilegeCheckerofPhoneUser extends CameraPrivilegeChecker<PhoneUser>
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
		
		if ( r.getPhoneuserid() == null )
			return false;
		
		if ( r.getPhoneuserid().equals(user.getPhoneuserid()))
			return true ;
		
		List<Integer> lst = PhoneUserHelper.querybySharetoPhoneuserid(user.getPhoneuserid());
		if ( lst.contains(r.getPhoneuserid()))
			return true ;
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		List<ZWaveDeviceShare> zdsl = zdss.querybyCameraid(cameraid);
		
		if ( zdsl == null || zdsl.size() == 0 )
			return false ;
		
		for ( ZWaveDeviceShare zds : zdsl )
			if ( zds.getTouserid() == user.getPhoneuserid() )
				return true ;
		
		return false;
	}

}
