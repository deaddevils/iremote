package com.iremote.common.taskmanager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThreadNameHelper {

	private static Log log = LogFactory.getLog(ThreadNameHelper.class);
	
	//private final static String PREFIX = "taskid";
	private static ThreadLocal<String> threadname = new ThreadLocal<String>() ;
	
	public static void changeThreadname(Object task)
	{
		if ( log.isInfoEnabled() == false )
			return ;
		
		//clearThreadname();
		
		long tid = getTaskIndentify(task);
		
		if ( tid == 0 )
			return ;
		
		String tn = Thread.currentThread().getName();
		threadname.set(tn);
		
		Thread.currentThread().setName(String.format("%s-%d-%d", task.getClass().getSimpleName() , Thread.currentThread().getId(), tid));
		log.trace("Thread begin");
	}
	
	private static long getTaskIndentify(Object task)
	{
		try
		{
			if ( PropertyUtils.isReadable(task, "taskIndentify"))
				return (Long)PropertyUtils.getProperty(task, "taskIndentify");
		}
		catch(Throwable t )
		{
			log.error(t.getMessage());
		}
		return 0 ;
	}
	
	public static void clearThreadname()
	{
		if ( log.isInfoEnabled() == false )
			return ;

		log.trace("Thread end");
		
		String tn = threadname.get();
		if ( tn == null || tn.length() == 0  )
			return ;
		Thread.currentThread().setName(tn);
		threadname.set(null);
	}
}
