package com.iremote.vo;

public class ZWaveDeviceExtendInfo 
{
	private int zwavedeviceid;
	private int nuid ;
	private String devicetype;
	private String extendinfo;
	
	public ZWaveDeviceExtendInfo(int zwavedeviceid, int nuid, String devicetype, String extendinfo) {
		super();
		this.zwavedeviceid = zwavedeviceid;
		this.nuid = nuid;
		this.devicetype = devicetype;
		this.extendinfo = extendinfo;
	}
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public int getNuid() {
		return nuid;
	}
	public void setNuid(int nuid) {
		this.nuid = nuid;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getExtendinfo() {
		return extendinfo;
	}
	public void setExtendinfo(String extendinfo) {
		this.extendinfo = extendinfo;
	}
	
	
}
