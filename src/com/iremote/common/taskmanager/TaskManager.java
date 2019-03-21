package com.iremote.common.taskmanager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.iremote.common.taskmanager.db.DefaultDbSessionFactory;

public class TaskManager {

	private static ExecutorService exeservice = Executors.newCachedThreadPool();
	
	public static void addTask(Runnable r)
	{
		exeservice.execute(new TaskWrap(r , new DefaultDbSessionFactory()));
	}
}
