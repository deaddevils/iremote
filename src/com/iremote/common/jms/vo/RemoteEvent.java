package com.iremote.common.jms.vo;

import java.util.Date;

public class RemoteEvent 
{
	private String deviceid ;
	private Date eventtime;
	private long taskid ;
	private int phoneuserid;
	
	public RemoteEvent() 
	{
		super();
	}
	
	public RemoteEvent(String deviceid, Date eventtime , long taskid) 
	{
		super();
		this.deviceid = deviceid;
		this.eventtime = eventtime;
		this.taskid = taskid ;
	}
	
	public RemoteEvent(String deviceid, Date eventtime , int phoneuserid , long taskid) 
	{
		super();
		this.deviceid = deviceid;
		this.eventtime = eventtime;
		this.phoneuserid = phoneuserid;
		this.taskid = taskid ;
	}

	public long getTaskIndentify() {
		return taskid;
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
	public void setTaskIndentify(long taskIndentify)
	{
		this.taskid = taskIndentify;
	}
	public int getPhoneuserid()
	{
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid)
	{
		this.phoneuserid = phoneuserid;
	}
	
	
}
