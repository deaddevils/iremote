package com.iremote.common.taskmanager;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.taskmanager.queue.ITaskQueue;

public class TaskQueueClear<T extends Runnable> implements Runnable
{
	private static Log log = LogFactory.getLog(TaskQueueClear.class);
	
	private Map<String , ITaskQueue<T>> taskmap;
	private long idletimeoutmillions ;
	
	public TaskQueueClear(Map<String, ITaskQueue<T>> taskmap , long idletimeoutmillions)
	{
		super();
		this.taskmap = taskmap;
		this.idletimeoutmillions = idletimeoutmillions;
	}

	@Override
	public void run()
	{
		synchronized(taskmap)
		{
			int t = 0 , l = 0;
			
			long nt = System.currentTimeMillis();
			for (Iterator<Map.Entry<String, ITaskQueue<T>>> it = taskmap.entrySet().iterator(); it.hasNext(); )
			{
				Map.Entry<String, ITaskQueue<T>> e = it.next();
				ITaskQueue<T> que = e.getValue();
				
				if ( que.getSize() > 0  || nt - e.getValue().getLastActiveTime() < idletimeoutmillions )
				{
					l ++ ;
					continue ;
				}
				else 
				{
					t ++ ;
					it.remove();
				}
			}
			log.info(String.format("remove %d queue, left %d(%d)", t , l , this.hashCode()));
		}

	}

}
