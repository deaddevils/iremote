package com.iremote.common.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ThreadManager implements Runnable {
  
	private static Log log = LogFactory.getLog(ThreadManager.class);
	private static Map<String , Thread > map = new ConcurrentHashMap<String , Thread>();
	private static Map<String , Runnable > runnermap = new ConcurrentHashMap<String , Runnable>();
	
	static
	{
		keepRunning("threadmanager" , new ThreadManager());
	}
	
	
	
	public static void keepRunning(String name , Runnable runner)
	{
		log.info("Start " + name);
		Thread t = new Thread(runner);
		t.start();
		
		map.put(name, t);
		runnermap.put(name, runner);
	}
	
	@Override
	public void run() 
	{
		for ( ; ; )
		{
			StringBuffer sb = new StringBuffer();
			
			List<String> lst = new ArrayList<String>(map.size());
			lst.addAll(map.keySet());
			
			for ( String name : lst )
			{
				Thread t = map.get(name);
				if ( t.isAlive() )
					sb.append(name).append(" ");
				else 
				{
					log.error(name + " stoped , restart....");
					keepRunning(name , runnermap.get(name));
				}	
			}
			
			sb.append("are alive");
			log.info(sb.toString());
			
			try { 
				Thread.sleep(1000 * 60 );
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				return ;
			}
			if ( Thread.currentThread().isInterrupted())
				return ;
		}
		
		
	}
	
	public static void stopall()
	{
		for ( Thread t : map.values())
			t.interrupt();
	}
}
