package com.iremote.common.taskmanager;

import java.util.Map;
import java.util.concurrent.BlockingDeque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.taskmanager.queue.ITaskQueue;

public class TaskTransfer<T extends Runnable> implements Runnable
{
	private static Log log = LogFactory.getLog(TaskTransfer.class);
	
	private Map<String , ITaskQueue<T>> map ;
	private Object waitobj;
	protected BlockingDeque<ITaskQueue<T>> taskqueue;
	
	public TaskTransfer(Map<String , ITaskQueue<T>> map , BlockingDeque<ITaskQueue<T>> taskqueue , Object waitobj)
	{
		this.map = map ;
		this.taskqueue = taskqueue;
		this.waitobj = waitobj;
	}

	@Override
	public void run()
	{
		for ( ; ; )
		{
			if ( this.taskqueue.isEmpty() )
			{
				for ( ITaskQueue<T> q : map.values())
				{
					if ( q.getSize() == 0 )
						continue;
					if ( !isReady(q))
						continue;
					if ( q.getowner() == false )
						continue ;
					q.release();
					taskqueue.add(q);
					
//					T r = getTask(q);
//					if ( r != null )
//						taskqueue.add(r);
				}
			}
			
			synchronized(waitobj)
			{
				try
				{
					waitobj.wait(1000);
				} 
				catch (InterruptedException e)
				{
					log.error(e.getMessage());
					return ;
				}
			}
			
			if ( Thread.currentThread().isInterrupted())
				return ;
		}
	}
	
	protected boolean isReady(ITaskQueue<T> q)
	{
		return true ;
	}

}
