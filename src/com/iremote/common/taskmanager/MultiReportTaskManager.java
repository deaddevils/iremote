package com.iremote.common.taskmanager;

public class MultiReportTaskManager<T extends IMulitReportTask> extends QueueTaskManager<IMulitReportTask> 
{
//	protected void init(int threadnumber )
//	{
//		for ( int i = 0 ; i < 10 && i < threadnumber ; i ++ )
//			exeservice.execute(new MultiReportTaskRunner(taskmap ,taskqueue, waitobj));
//	}
	
	@Override
	public void addTask(String key , IMulitReportTask task ) throws IllegalStateException 
	{
		MultiReportTaskQueue tq = getQueue(key);
		
		if ( tq.merge(task) == false )
			super.addTask(key, task);
	}

	@Override
	protected MultiReportTaskQueue getQueue(String key) 
	{
		MultiReportTaskQueue taskqueue = (MultiReportTaskQueue)taskmap.get(key);
		if ( taskqueue != null )
			return taskqueue;
		
		synchronized(taskmap)
		{
			if ( taskmap.containsKey(key))
				return (MultiReportTaskQueue)taskmap.get(key);
			taskqueue = new MultiReportTaskQueue();
			taskmap.put(key, taskqueue);
			return taskqueue;
		}
	}

	@Override
	protected void initTaskTransfer()
	{
		exeservice.execute(new MultiReportTaskTransfer<IMulitReportTask>(taskmap ,taskqueue , waitobj ));
	}
	
}
