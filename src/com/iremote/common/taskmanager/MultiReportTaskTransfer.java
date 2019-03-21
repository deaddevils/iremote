package com.iremote.common.taskmanager;

import java.util.Map;
import java.util.concurrent.BlockingDeque;

import com.iremote.common.taskmanager.queue.ITaskQueue;

public class MultiReportTaskTransfer<T extends IMulitReportTask> extends TaskTransfer<T>
{

	public MultiReportTaskTransfer(Map<String, ITaskQueue<T>> map, BlockingDeque<ITaskQueue<T>> taskqueue, Object waitobj)
	{
		super(map, taskqueue, waitobj);
	}

	@Override
	protected boolean isReady(ITaskQueue<T> tq)
	{
		T t = tq.peekTask();
		if ( t == null )
			return false ;
		return t.isReady();
	}
	
}
