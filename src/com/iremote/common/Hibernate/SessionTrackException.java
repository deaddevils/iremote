package com.iremote.common.Hibernate;

public class SessionTrackException extends Exception {

	private long threadid ;
	private String threadname ;
	
	public SessionTrackException() {
		super();
		this.threadid = Thread.currentThread().getId();
		threadname = Thread.currentThread().getName();
	}

	@Override
	public String getMessage() {
		return String.format("thread id = %d , thread name = %s " , threadid , threadname);
	}
	
	
	
}
