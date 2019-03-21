package com.iremote.common.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiObjectStore<T extends IExpire>
{
	private Map<String , LinkedBlockingQueue<T>> objStore = new ConcurrentHashMap<String , LinkedBlockingQueue<T>>();
	
	public void put(String key , T t)
	{
		LinkedBlockingQueue<T> lst = objStore.get(key);
		if ( lst == null )
		{
			lst = new LinkedBlockingQueue<T>();
			objStore.put(key, lst);
		}
		lst.add(t);
	}

	public List<T> get(String key)
	{
		return get(key , null );
	}
	
	private List<T> get(String key , T remove)
	{
		LinkedBlockingQueue<T> que = objStore.get(key);
		
		if ( que == null )
			return null;
		
		List<T> rstl = new ArrayList<T>();
		List<T> rl = new ArrayList<T>();

		long now = System.currentTimeMillis();
		
		for ( T wp : que )
		{		
			if ( wp.getExpireTime() != 0 && wp.getExpireTime() < now )
			{
				rl.add(wp);
				continue;
			}

			rstl.add(wp);
			
			if ( remove != null && remove == wp )
				rl.add(wp);
		}
		
		que.removeAll(rl);

		if ( que.isEmpty())
			objStore.remove(key);
		
		return rstl ;
	}
	
	public List<T> remove(String key , T t)
	{
		return get(key, t);
	}
}
