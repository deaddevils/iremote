package com.iremote.common.jms.vo;

import java.util.Date;

public class InfoChange 
{
	private String deviceid ;
	private Date eventtime;
	private long taskid ;
	
	public InfoChange() {
		super();
	}
	public InfoChange(String deviceid, Date eventtime , long taskid) {
		super();
		this.deviceid = deviceid;
		this.eventtime = eventtime;
		this.taskid = taskid ;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public Date getEventtime() {
		return eventtime;
	}
	public void setEventtime(Date eventtime) {
		this.eventtime = eventtime;
	}

	public long getTaskIndentify() {
		return taskid;
	}

	
	
}
