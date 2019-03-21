package com.iremote.common;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil 
{
	public static String getString(JSONObject json , String key)
	{
		if ( json == null || StringUtils.isBlank(key))
			return null;
		String[] ks = key.split("\\.");
		
		for ( int i = 0 ; i < ks.length - 1 ; i ++ )
		{
			json = json.getJSONObject(ks[i]);
			if ( json == null )
				return null ;
		}
		
		return json.getString(ks[ks.length-1]);
	}
	
	public static Integer getInteger(JSONObject json , String key)
	{
		String vs = getString(json  , key);
		if ( vs == null )
			return null ;
		return Integer.valueOf(vs);
	}
}
