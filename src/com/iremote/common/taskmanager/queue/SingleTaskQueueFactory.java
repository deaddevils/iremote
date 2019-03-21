package com.iremote.common.taskmanager.queue;

public class SingleTaskQueueFactory<T extends Runnable>  implements ITaskQueueFactory<T> {

	@Override
	public ITaskQueue<T> creatTaskQueue() {
		return new SingleTaskQueue<T>();
	}

}
