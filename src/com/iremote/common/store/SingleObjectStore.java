package com.iremote.common.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingleObjectStore<T extends IExpire>
{
	private Map<String , T> objStore = new ConcurrentHashMap<String , T>();
	
	public void put(String key , T t)
	{
		objStore.put(key , t);
	}

	public T get(String key)
	{
		T t = objStore.get(key );
		if ( t != null 
				&& t.getExpireTime() != 0 
				&& t.getExpireTime() < System.currentTimeMillis())
		{
			objStore.remove(key);
			return null ;
		}
		return t ;
	}
	
	public T remove(String key)
	{
		return objStore.remove(key);
	}
}
