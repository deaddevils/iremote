package com.iremote.common.taskmanager;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.taskmanager.db.DefaultDbSessionFactory;
import com.iremote.common.taskmanager.queue.ITaskQueue;

public class StatusfulQueueTaskRunner extends QueueTaskRunner<StatusfulTaskWrap> {
	
	private static Log log = LogFactory.getLog(StatusfulQueueTaskRunner.class);
	private Map<String, ITaskQueue<StatusfulTaskWrap>> map ;
	
	public StatusfulQueueTaskRunner(Map<String, ITaskQueue<StatusfulTaskWrap>> map ,Object waitobj) 
	{
		super(null,waitobj , new DefaultDbSessionFactory());
		this.map = map ;
	}
	
	@Override
	public void run() 
	{
		for ( ; ; )
		{
			int count = 0 ;
			for ( ITaskQueue<StatusfulTaskWrap> tq : map.values() )
			{
				if ( tq.getowner() == false )
					continue;
				
				try
				{
					StatusfulTaskWrap r = getTask(tq);
					if ( r != null )
					{
						if ( runTask(tq  , r) == true )
							count ++ ;
					}
				}
				catch(Throwable t)
				{
					log.error(t.getMessage(), t);
				}
				finally
				{
					tq.release();
				}
			}
			
			if ( count == 0 )
			{
				synchronized(waitobj)
				{
					try {
						waitobj.wait(100);
					} catch (InterruptedException e) {
						log.info(e.getMessage());
						return ;
					}
				}
			}
			
			if ( Thread.currentThread().isInterrupted())
			{
				log.info("Interrupted");
				return ;
			}
		}
		
	}
	
	protected StatusfulTaskWrap getTask(ITaskQueue<StatusfulTaskWrap> tq)
	{
		return tq.peekTask();
	}
	
	private boolean runTask(ITaskQueue<StatusfulTaskWrap> tq , StatusfulTaskWrap r)
	{
		if ( r != null )
		{
			if ( r.isFinished() )
			{
				tq.remove();
			}
			else if ( !r.isExecuted()) 
			{
				TaskWrap tw = new TaskWrap(r , super.sessionFactory);
				tw.run();
				return true;
			}
		}
		return false ;
	}

}
