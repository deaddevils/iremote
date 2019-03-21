package com.iremote.thirdpart.wcj.action;

import com.iremote.common.taskmanager.QueueTaskManager;

public class DoorlockPasswordTaskManager {

	private static QueueTaskManager<SetPasswordThread> tm = new QueueTaskManager<SetPasswordThread>();
	
	public static void addTask(String deviceid , SetPasswordThread t)
	{
		tm.addTask(deviceid, t);
	}
	
	public static void shutdown()
	{
		tm.shutdown();
	}
}
