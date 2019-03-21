package com.iremote.common.jms;

import com.iremote.common.taskmanager.QueueTaskManager;

public class JSMTaskManager 
{
	private static QueueTaskManager<Runnable> taskmgr = new QueueTaskManager<Runnable>();
	
	public static void addTask(String key , Runnable task ) throws IllegalStateException 
	{
		taskmgr.addTask(key, task);
	}
	
	public static void shutdown()
	{
		taskmgr.shutdown();
	}
}
