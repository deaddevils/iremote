package com.iremote.common.jms.vo;

public class InfraredDeviceEvent 
{
	private int infrareddeviceid;
	private String deviceid;
	private String applianceid ;
	
	
	
	public InfraredDeviceEvent() {
		super();
	}

	public InfraredDeviceEvent(int infrareddeviceid, String deviceid , String applianceid) {
		super();
		this.infrareddeviceid = infrareddeviceid;
		this.deviceid = deviceid;
		this.applianceid = applianceid ;
	}
	
	public int getInfrareddeviceid() {
		return infrareddeviceid;
	}
	public void setInfrareddeviceid(int infrareddeviceid) {
		this.infrareddeviceid = infrareddeviceid;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getApplianceid() {
		return applianceid;
	}

	public void setApplianceid(String applianceid) {
		this.applianceid = applianceid;
	}
	
	
}
