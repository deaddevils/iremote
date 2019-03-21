package com.iremote.common.taskmanager.queue;

public interface ITaskQueueFactory<T extends Runnable> 
{
	ITaskQueue<T> creatTaskQueue();
}
