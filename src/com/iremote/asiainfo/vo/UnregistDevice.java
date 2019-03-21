package com.iremote.asiainfo.vo;

public class UnregistDevice {

	private String deviceId;
	private String dateTime;
	
	public UnregistDevice(String deviceId, String dateTime) {
		super();
		this.deviceId = deviceId;
		this.dateTime = dateTime;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
