package com.iremote.common.processorstore;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassMapper<T> 
{
	private static Log log = LogFactory.getLog(ClassMapper.class);
	
	protected Map<String , Class<? extends T> > map = new HashMap<String , Class<? extends T> >();
	
	public void registProcessor(String key , Class<? extends T> clz)
	{
		map.put(key, clz);
	}
	
	public T getProcessor(String key)
	{
		if ( key == null || key.length() == 0 )
			return null ;
		Class<? extends T> cls = map.get(key) ;
		if ( cls == null )
			return null ;
		try {
			return cls.newInstance();
		} catch (InstantiationException e) {
			log.error(e.getMessage() ,e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage() ,e);
		}
		return null ;
	}
}
