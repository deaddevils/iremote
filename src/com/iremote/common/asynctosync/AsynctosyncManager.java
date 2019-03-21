package com.iremote.common.asynctosync;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Deprecated
public class AsynctosyncManager {

	private static Map<String , AsynctosyncWrap> map = new ConcurrentHashMap<String ,  AsynctosyncWrap>();
	private static Map<String , Object> result = new ConcurrentHashMap<String ,  Object>();
	
	private static AsynctosyncManager instance = new AsynctosyncManager();
	
	private AsynctosyncManager()
	{
		
	}
	
	public static AsynctosyncManager getInstance()
	{
		return instance;
	}
	
	public Object execute(IAsynctosync ats) throws Exception
	{
		ats = add(ats);
		
		try
		{
			ats.sendRequest() ;
		}
		catch(Exception t)
		{
			remove(ats);
			throw t;
		}
		
		synchronized(ats)
		{
			ats.wait(ats.getTimeoutMilliseconds());
		}
		
		remove(ats);
		
		return result.remove(getkey(ats));
	}
	
	public void onResponse(String key , Object response)
	{
		AsynctosyncWrap ats = map.get(key);
		
		if ( ats == null )
			return ;
		if ( ats.isAutodelete() )
		{
			remove(ats);
			return ;
		}
		result.put(key, response);
		synchronized(ats)
		{
			ats.notify();
		}
		
	}
	
	public void executewithoutwait(IAsynctosync ats) throws Exception
	{
		AsynctosyncWrap atsw = add(ats);
		atsw.setAutodelete(true);
		
		try
		{
			ats.sendRequest() ;
		}
		catch(Exception t)
		{
			remove(ats);
			throw t;
		}
	}
	
	private AsynctosyncWrap add(IAsynctosync ats)
	{
		String key = getkey(ats);
		AsynctosyncWrap a = new AsynctosyncWrap(ats);
		map.put(key, a);
		return a;
	}
	
	private void remove(IAsynctosync ats)
	{
		String key = getkey(ats);
		map.remove(key);
	}
	
	private String getkey(IAsynctosync ats)
	{
		String key = ats.getkey();
		if ( key == null )
			return "" ;
		return key;
	}
}
