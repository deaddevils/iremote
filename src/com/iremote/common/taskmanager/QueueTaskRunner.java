package com.iremote.common.taskmanager;

import java.util.concurrent.BlockingDeque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.taskmanager.db.IDbSessionFactory;
import com.iremote.common.taskmanager.queue.ITaskQueue;

public class QueueTaskRunner<T extends Runnable> implements Runnable {

	private static Log log = LogFactory.getLog(QueueTaskRunner.class);
	protected Object waitobj;
	protected IDbSessionFactory sessionFactory;
	protected BlockingDeque<ITaskQueue<T>> taskqueue;
	
	public QueueTaskRunner(BlockingDeque<ITaskQueue<T>> taskqueue ,Object waitobj, IDbSessionFactory sessionFactory) {
		
		super();
		this.waitobj = waitobj;
		this.sessionFactory = sessionFactory;
		this.taskqueue = taskqueue;
	}
	
	
	@Override
	public void run() 
	{
		for ( ; ; )
		{
			try
			{
				if ( taskqueue.isEmpty() )
				{
					synchronized(waitobj)
					{
						waitobj.notify();
					}
				}
				
				ITaskQueue<T> q = taskqueue.takeFirst();
				
				if ( q.getowner() == false )
					continue;
				try
				{
					T t = q.getTask();
					if ( t != null )
					{
						TaskWrap tw = new TaskWrap(t , sessionFactory);
						tw.run();
					}
				}
				finally
				{
					q.release();
				}
			}
			catch(InterruptedException e)
			{
				log.info(e.getMessage()); 
				return ;
			}
			catch(Throwable t )
			{
				log.error(t.getMessage(),t);
			}
			if ( Thread.currentThread().isInterrupted())
				return ;
		}
	}

}
