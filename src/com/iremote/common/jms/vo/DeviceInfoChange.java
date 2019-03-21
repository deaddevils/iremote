package com.iremote.common.jms.vo;

import java.util.Date;

public class DeviceInfoChange 
{
	private int zwavedeviceid;
	private String deviceid ;
	private String name;
	private String devicetype;
	private Date eventtime;
	private long taskid ;
	
	public DeviceInfoChange() {
		super();
	}
	public DeviceInfoChange(String deviceid, Date eventtime , long taskid) {
		super();
		this.deviceid = deviceid;
		this.eventtime = eventtime;
		this.taskid = taskid ;
	}
	
	public DeviceInfoChange(int zwavedeviceid, String deviceid, String name, String devicetype, Date eventtime,long taskid) {
		super();
		this.zwavedeviceid = zwavedeviceid;
		this.deviceid = deviceid;
		this.name = name;
		this.devicetype = devicetype;
		this.eventtime = eventtime;
		this.taskid = taskid;
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
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	
	
}
