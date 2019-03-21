package com.iremote.common.Hibernate;

import java.util.Date;

public class SessionTracker {

	private SessionTrackException tracker;
	private Date time = new Date() ;
	
	public SessionTracker(SessionTrackException tracker) {
		super();
		this.tracker = tracker;
	}

	public SessionTrackException getTracker() {
		return tracker;
	}

	public Date getTime() {
		return time;
	}
	
	
}
