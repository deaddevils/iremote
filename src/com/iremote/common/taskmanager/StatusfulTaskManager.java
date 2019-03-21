package com.iremote.common.taskmanager;

public class StatusfulTaskManager<T extends IStatusfulTask> extends QueueTaskManager<StatusfulTaskWrap> {
	
	protected void init(int threadnumber )
	{
		for ( int i = 0 ; i < 20 && i < threadnumber ; i ++ )
			exeservice.execute(new StatusfulQueueTaskRunner(taskmap ,waitobj));
	}
	
	public void addTask(String key , T task ) throws IllegalStateException 
	{	
		super.addTask(key, new StatusfulTaskWrap(task));
	}

	@Override
	protected void initTaskTransfer()
	{
		//Do nothing
	}
}
