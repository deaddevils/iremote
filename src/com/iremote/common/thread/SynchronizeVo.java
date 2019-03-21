package com.iremote.common.thread;

import java.util.Date;

public class SynchronizeVo {

	private Object response;
	private Date date ;

	public void setDate(Date date) {
		this.date = date;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public Date getDate() {
		return date;
	}
	
	
}
