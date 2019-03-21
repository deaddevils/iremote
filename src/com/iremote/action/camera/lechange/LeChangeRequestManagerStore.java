package com.iremote.action.camera.lechange;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.iremote.common.IRemoteConstantDefine;
import org.apache.commons.lang3.StringUtils;

import com.iremote.common.encrypt.AES;

public class LeChangeRequestManagerStore 
{
	private static LeChangeRequestManagerStore instance = new LeChangeRequestManagerStore();

	private Map<Integer , String[]> LeChangeMap = new ConcurrentHashMap<Integer , String[]>();
	
	public static LeChangeRequestManagerStore getInstance()
	{
		return instance ;
	} 
	
	public void initLeChangeInterface(int platform , String appid, String appSecret )
	{
		if ( StringUtils.isBlank(appid) || StringUtils.isBlank(appSecret))
			return ;
		LeChangeMap.put(platform, new String[]{AES.decrypt2Str(appid) , AES.decrypt2Str(appSecret)});
	}

	public void initLeChangeInterface(int platform , String appId, String appSecret , String abroadAppId, String abroadAppSecret)
	{
		if ( StringUtils.isBlank(appId) || StringUtils.isBlank(appSecret))
			return ;
		LeChangeMap.put(platform, new String[]{AES.decrypt2Str(appId) , AES.decrypt2Str(appSecret), AES.decrypt2Str(abroadAppId), AES.decrypt2Str(abroadAppSecret)});
	}

	public LeChangeInterface getProcessor(Integer platform)
	{
		String[] str = LeChangeMap.get(platform);
		if ( str == null || str.length < 2)
			return null ;
		return new LeChangeInterface(str[0] , str[1] , "");
	}

	public LeChangeInterface getProcessor(Integer platform, String devicetype)
	{
		devicetype = devicetype == null? IRemoteConstantDefine.CAMERA_DEVICE_TYPE_DOMESTIC : devicetype;

		String[] str = LeChangeMap.get(platform);
		if ( str == null || str.length < 2){
			return null ;
		}
		if (IRemoteConstantDefine.CAMERA_DEVICE_TYPE_ABROAD.equals(devicetype)) {
			if (str.length < 4) {
				return null;
			}
			return new LeChangeInterface(str[2] , str[3] , "", devicetype);
		}
		return new LeChangeInterface(str[0] , str[1] , devicetype);
	}
}
