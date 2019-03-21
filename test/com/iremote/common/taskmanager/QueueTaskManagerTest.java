package com.iremote.common.taskmanager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueueTaskManagerTest {

	private static Log log = LogFactory.getLog(QueueTaskManagerTest.class);
	private static QueueTaskManager<Runnable> tskmgr = new QueueTaskManager<Runnable>();
	
	public static void main(String arg[])
	{
		for ( int i = 0 ; i < 10 ; i ++ )
			tskmgr.addTask(String.format("testtask%d" , i % 10 ), new Testtask(i));
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.info("");
		
		for ( int i = 0 ; i < 10 ; i ++ )
			tskmgr.addTask(String.format("testtask%d" , i % 10 ), new Testtask(i));
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.info("");
		
		for ( int i = 0 ; i < 10 ; i ++ )
			tskmgr.addTask(String.format("testtask%d" , i % 10 ), new Testtask(i));
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.info("");
		
		for ( int i = 0 ; i < 100 ; i ++ )
			tskmgr.addTask(String.format("testtask%d" , i % 10 ), new Testtask(i));

	}
}
