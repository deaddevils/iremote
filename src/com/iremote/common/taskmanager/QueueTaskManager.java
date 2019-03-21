package com.iremote.common.taskmanager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.schedule.ScheduleManager;
import com.iremote.common.taskmanager.db.DefaultDbSessionFactory;
import com.iremote.common.taskmanager.db.IDbSessionFactory;
import com.iremote.common.taskmanager.queue.DefaultTaskQueueFactory;
import com.iremote.common.taskmanager.queue.ITaskQueue;
import com.iremote.common.taskmanager.queue.ITaskQueueFactory;

public class QueueTaskManager<T extends Runnable> {

	private static Log log = LogFactory.getLog(QueueTaskManager.class);
	private static int idletimeout = 180 ; 
	protected Map<String , ITaskQueue<T>> taskmap = new  ConcurrentHashMap<String , ITaskQueue<T>>();
	protected BlockingDeque<ITaskQueue<T>> taskqueue = new java.util.concurrent.LinkedBlockingDeque<ITaskQueue<T>>();
	
	protected ExecutorService exeservice = Executors.newCachedThreadPool();
	private final int MAX_THREAD_COUNT = 20;
	protected Object waitobj = new Object();
	
	private int threadnumber = 3 ;
	private IDbSessionFactory sessionFactory = new DefaultDbSessionFactory();
	private ITaskQueueFactory<T> taskQueueFactory = new DefaultTaskQueueFactory<T>();
	
	private boolean isInited = false ;

	public QueueTaskManager()
	{
		init(3);
		initTaskTransfer();
	}
	
	public QueueTaskManager( int threadnumber)
	{
		init(threadnumber);
		initTaskTransfer();
	}
	
	public QueueTaskManager( int threadnumber , boolean rummediately , 
			IDbSessionFactory sessionFactory , ITaskQueueFactory<T> taskQueueFactory)
	{
		
		this.threadnumber = threadnumber;
		this.sessionFactory = sessionFactory;
		this.taskQueueFactory = taskQueueFactory;
		if ( rummediately )
			init(threadnumber);
		initTaskTransfer();
	}
	
	protected void initTaskTransfer()
	{
		exeservice.execute(new TaskTransfer<T>(taskmap ,taskqueue , waitobj ));
	}
	
	public void start()
	{
		init(threadnumber);
	}
	
	
	protected synchronized void init(int threadnumber )
	{
		if ( isInited )
			return ;
		
		isInited = true ;
		for ( int i = 0 ; i < MAX_THREAD_COUNT && i < threadnumber ; i ++ )
			exeservice.execute(new QueueTaskRunner<T>(taskqueue, waitobj , sessionFactory));	
		
		ScheduleManager.excuteEvery(idletimeout, this.hashCode(), "TaskQueueClear",  new TaskQueueClear<T>(taskmap , idletimeout * 1000)); 
	} 
	
	public void shutdown()
	{
		exeservice.shutdownNow();
	}
	
	public void addTask(String key , T task ) throws IllegalStateException 
	{
		synchronized(taskmap)
		{
			ITaskQueue<T> tq = getQueue(key);
			tq.addTask(task);
		}
		synchronized(waitobj)
		{
			waitobj.notify();
		}
	}
	
	protected ITaskQueue<T> getQueue(String key)
	{
		ITaskQueue<T> taskqueue = taskmap.get(key);
		if ( taskqueue != null )
			return taskqueue;
		
//		synchronized(taskmap)
//		{
//			if ( taskmap.containsKey(key))
//				return taskmap.get(key);
			
			taskqueue = taskQueueFactory.creatTaskQueue();
			taskmap.put(key, taskqueue);
			return taskqueue;
//		}
	}
}
