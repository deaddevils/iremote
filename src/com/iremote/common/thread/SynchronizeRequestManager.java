package com.iremote.common.thread;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SynchronizeRequestManager {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(SynchronizeRequestManager.class);
	
	private static Map<String , SynchronizeVo> synchronizeObject = new ConcurrentHashMap<String , SynchronizeVo>();
	
	public static void regist(String id , String group) throws KeyCrashException
	{
		String key = makeKey(id , group);
		if ( synchronizeObject.containsKey(key))
		{
			SynchronizeVo vo = synchronizeObject.get(key);
			if ( System.currentTimeMillis() - vo.getDate().getTime() < 30 * 1000 )
				throw new KeyCrashException();
			synchronizeObject.remove(key);
		}
		
		SynchronizeVo vo = new SynchronizeVo();
		vo.setDate(new Date());
		
		synchronizeObject.put(key, vo);
	}
	
	public static Object getResponse(String id , String group) throws InterruptedException
	{
		return getResponse(id, group , 6);
	}
	
	public static Object getResponse(String id , String group , int timeoutsecond) throws InterruptedException
	{
		String key = makeKey(id , group);
		SynchronizeVo vo = synchronizeObject.get(key);
		if ( vo == null )
			return null ;
		
		if ( vo.getResponse() == null )
		{
			synchronized(vo)
			{
				try {
					if ( vo.getResponse() == null )
						vo.wait(timeoutsecond*1000);
				} catch (InterruptedException e) {
					throw e;
				} 
			}
		}
		synchronizeObject.remove(key);
		return vo.getResponse();
	}
	
	public static void remove(String id , String group)
	{
		String key = makeKey(id , group);
		synchronizeObject.remove(key);
	}
	
	public static Object synchronizeRequest(ISynchronizeRequest request) throws KeyCrashException, InterruptedException, IOException, TimeoutException
	{
		String key = request.getkey();
		if ( synchronizeObject.containsKey(key))
			throw new KeyCrashException();
		
		SynchronizeVo obj = new SynchronizeVo();
		
		synchronizeObject.put(key, obj);
		
		synchronized(obj)
		{
			try {
				request.sendRequest();
				obj.wait(6*1000);
			} catch (InterruptedException e) {
				throw e;
			} catch (BufferOverflowException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			} catch (TimeoutException e) {
				throw e;
			}
		}
		
		synchronizeObject.remove(key);
		return obj.getResponse();
	}
	
	public static void response(String key , Object response)
	{
		SynchronizeVo obj = synchronizeObject.get(key);
		if ( obj == null )
			return ;
		
		synchronized(obj)
		{
			obj.setResponse(response);
			obj.notifyAll();
		}
		
	}
	
	public static void response(String id , String group , Object response)
	{
		String key = makeKey(id , group);
		SynchronizeVo obj = synchronizeObject.get(key);
		if ( obj == null )
			return ;
		
		synchronized(obj)
		{
			obj.setResponse(response);
			obj.notifyAll();
		}
		
	}
	
	private static String makeKey(String id , String group)
	{
		if ( id == null )
			id = "";
		if ( group == null ) 
			group = "";
		return String.format("id_%s_group_%s", id , group);
	}
}
