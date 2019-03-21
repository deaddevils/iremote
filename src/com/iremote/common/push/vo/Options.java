package com.iremote.common.push.vo;

public class Options {

	private int time_to_live = 864000;
	private boolean apns_production = true ;
	
	public int getTime_to_live() {
		return time_to_live;
	}

	public synchronized boolean isApns_production() {
		return apns_production;
	}

	public void setApns_production(boolean apns_production) {
		this.apns_production = apns_production;
	}
}
