package com.iremote.thirdpart.rentinghouse.vo;

import java.util.ArrayList;
import java.util.List;

import com.iremote.domain.ZWaveSubDevice;

public class DeviceInfoChangeEvent {

	private int zwavedeviceid;
	private String deviceid;
	private String name;
	private String devicetype;
	private List<ZWaveSubDevice> zwavesubdevice = new ArrayList<ZWaveSubDevice>();
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public List<ZWaveSubDevice> getZwavesubdevice() {
		return zwavesubdevice;
	}
	public void setZwavesubdevice(List<ZWaveSubDevice> zwavesubdevice) {
		this.zwavesubdevice = zwavesubdevice;
	}

	
}
