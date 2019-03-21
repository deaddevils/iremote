package com.iremote.common.sms;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SMSManageThread implements Runnable {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(SMSManageThread.class);
	
	private static Queue<SMSVo> queue = new  ConcurrentLinkedQueue<SMSVo>();
	private static Thread thread;
	private static SMSManageThread instance = new SMSManageThread();
	private ExecutorService exeservice = Executors.newCachedThreadPool();
	
	private SMSManageThread()
	{
		
	}
	
	public static SMSManageThread getInstance()
	{
		return instance;
	}
	
	public static void start()
	{
		thread = new Thread(instance);
		thread.start();
	}
	
	public static void addSMS(SMSVo sms)
	{
		queue.add(sms);
	}
	
	@Override
	public void run() 
	{
		for ( ; ; )
		{
			SMSVo vo = queue.poll();

			exeservice.execute(new SMSSendThread(vo));

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				return ;
			}
			
			if ( Thread.currentThread().isInterrupted())
				return ;
		}
	}

}
