package com.iremote.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.domain.Remote;
import com.iremote.service.RemoteService;

public class GatewayUtils
{
	private static Map<String , Integer> DeviceIdPlatformMap = new ConcurrentHashMap<String , Integer>();

	public static boolean isAirCondition(Remote r)
	{
		return ( r.getRemotetype() == IRemoteConstantDefine.GATEWAY_TYPE_AIR_QUALITY);
	}
	
	public static boolean isLockGateway(Remote r)
	{
		return isCobbeLock(r) || isTongxinLock(r);
	}
	
	public static boolean isLockGateway(String deviceid)
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		return isLockGateway(r);
	}
	
	public static boolean isIRemote(Remote r )
	{
		if ( r == null )
			return false; 
		if ( r.getRemotetype() == IRemoteConstantDefine.GATEWAY_TYPE_NORMAL  //cobbe lock do not report remotetype 
				&& !r.getDeviceid().startsWith("iRemote30061"))
			return true;
		return false ;
	}
	
	public static boolean isCobbeLock(String deviceid)
	{
		if ( StringUtils.isBlank(deviceid))
			return false ;
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		return isCobbeLock(r);
	}
	
	public static boolean isCobbeLock(Remote r)
	{
		if ( r == null )
			return false; 
		if ( isTongxinLock(r))
			return true;
		if ( r.getRemotetype() == IRemoteConstantDefine.GATEWAY_TYPE_COBBE_LOCK)
			return true;
		else if ( r.getRemotetype() == IRemoteConstantDefine.GATEWAY_TYPE_NORMAL  //cobbe lock do not report remotetype 
				&& r.getDeviceid().startsWith("iRemote30061"))
			return true;
		else if (r.getRemotetype() == IRemoteConstantDefine.GATEWAY_TYPE_DRESS_HELPER)
			return true;
		return false ;
	}
	
	public static boolean isTongxinLock(Remote r)
	{
		if ( r == null )
			return false; 
		return ( r.getRemotetype() == IRemoteConstantDefine.GATEWAY_TYPE_TONGXIN_LOCK);
	}
	
	public static boolean isAccessControl(Remote r)
	{
		if ( r == null )
			return false ;
		return ( r.getRemotetype() == IRemoteConstantDefine.GATEWAY_TYPE_ACCESS_CONTROL);
	}
	
	public static void initRemotePlatformMap(int platform , String deviceprex)
	{
		if ( StringUtils.isBlank(deviceprex))
			return ;
		JSONArray ja = JSON.parseArray(deviceprex);
		
		for ( int i = 0 ; i < ja.size() ; i ++ )
			DeviceIdPlatformMap.put(ja.getString(i), platform);
	}
	
	public static int getRemotePlatform(String deviceid)
	{
		if ( ServerRuntime.getInstance().getUniversalplatform() != 0 )
			return ServerRuntime.getInstance().getUniversalplatform();
		
		if (deviceid == null )
			return IRemoteConstantDefine.PLATFORM_ZHJW ;

		for ( String key : DeviceIdPlatformMap.keySet())
		{
			if ( deviceid.startsWith(key))
				return DeviceIdPlatformMap.get(key);
		}
		
		return IRemoteConstantDefine.PLATFORM_ZHJW ;
	}
}
