package com.iremote.common.taskmanager.queue;

public class SingleTaskQueue<T extends Runnable> extends TaskQueue<T> {

	public SingleTaskQueue() {
		super(1);
	}

	@Override
	protected void onExceedMaxSize() {
		//Do nothing
	}

	
}
