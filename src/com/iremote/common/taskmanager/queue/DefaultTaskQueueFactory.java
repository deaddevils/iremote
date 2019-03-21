package com.iremote.common.taskmanager.queue;

public class DefaultTaskQueueFactory<T extends Runnable> implements ITaskQueueFactory<T> {

	@Override
	public ITaskQueue<T> creatTaskQueue() 
	{
		return new TaskQueue<T>();
	}

}
