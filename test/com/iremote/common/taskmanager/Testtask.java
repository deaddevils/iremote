package com.iremote.common.taskmanager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Testtask implements Runnable {

	private static Log log = LogFactory.getLog(Testtask.class);
	
	private int group ;

	public Testtask(int group) {
		super();
		this.group = group;
	}

	@Override
	public void run() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
		}
		
		log.info(String.format("index %d" ,group));
		
		
	}

}
