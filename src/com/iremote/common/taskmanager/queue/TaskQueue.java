package com.iremote.common.taskmanager.queue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TaskQueue<T extends Runnable> implements ITaskQueue<T>
{
	protected int maxqueuesize = 5000 ;
	
	private static Log log = LogFactory.getLog(TaskQueue.class);
	
	protected ConcurrentLinkedQueue<T> que = new ConcurrentLinkedQueue<T>();
	private Lock lock = new ReentrantLock();

	private long lastactivetime = System.currentTimeMillis();
	
	public TaskQueue() {
		super();
	}
	
	public TaskQueue(int maxqueuesize) {
		super();
		this.maxqueuesize = maxqueuesize;
	}

	public void addTask(T task)
	{
		lastactivetime = System.currentTimeMillis();
		if ( que.size() < maxqueuesize )
			que.add(task);
		else 
			onExceedMaxSize();
	}
	
	protected void onExceedMaxSize()
	{
		log.error("Exceed max queue size,discard task");
	}
	
	public T getTask()
	{
		return que.poll();
	}
	
	public T peekTask()
	{
		return que.peek();
	}
	
	public Runnable remove()
	{
		return que.remove();
	}
	
	public boolean getowner()
	{
		return lock.tryLock();
	}
	
	public void lock()
	{
		lock.lock();
	}
	
	public void release()
	{
		lock.unlock();
	}
	
	public int getSize()
	{
		return que.size();
	}

	@Override
	public long getLastActiveTime()
	{
		return lastactivetime;
	}
}
