package com.iremote.dataprivilege.zwavedevice;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.domain.Remote;
import com.iremote.domain.ThirdPart;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;

public class DevicePrivilegeCheckerforThirdparter extends DeviceOperationPrivilegeChecker<ThirdPart>
{
	@Override
	public void SetParameters(Map<String , String> parameters)
	{
		super.SetParameters(parameters);
		if ( StringUtils.isNotBlank(parameters.get("lockid")) )
			zwavedeviceid = Integer.valueOf(parameters.get("lockid"));
		
	}
	@Override
	public boolean checkprivilege()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		if ( zwavedeviceid != 0 )
		{
			device = zds.query(zwavedeviceid);
				
			if ( device == null )
				return true ;
			
			super.deviceid = device.getDeviceid();
		}
		
		if ( ServerRuntime.getInstance().getAmetaallthirdpartid() != 0 
				&&IRemoteConstantDefine.PLATFORM_AMETA == Utils.getRemotePlatform(deviceid) 
				&& super.user.getCode().equals("tp_jwzh_ameta_all"))
			return true;
		
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote cr = crs.querybyDeviceid(deviceid);
		if ( cr == null )
			return false ;
		
		if ( cr.getThirdpartid() == super.user.getThirdpartid() )
			return true ;
		
		if ( super.user.getThirdpartid() == 16  // Hemu youth apartment PMS third part ID 
				&& cr.getThirdpartid() == 7     // Dorlink zufang platform third part ID
				&& cr.getComunityid() == 211 )  // Hemu youth apartment community ID
			return true ;
		

				
//		if ( crs.query(super.user.getThirdpartid() , super.deviceid) != null ) 
//			return true ;

		if ( super.user.getThirdpartid() != 1 ) // Tecus third part id
			return false ;
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		if ( r != null && r.getPlatform() == IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN)
			return true;
		return false ;
	}

}
