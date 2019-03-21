package com.iremote.common.taskmanager.queue;

public interface ITaskQueue<T extends Runnable> {

	void addTask(T task);
	T getTask();
	T peekTask();
	Runnable remove();
	boolean getowner();
	void lock();
	void release();
	int getSize();
	long getLastActiveTime();
}
