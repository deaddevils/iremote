package com.iremote.thirdpart.rentinghouse.vo;

import java.util.ArrayList;
import java.util.List;

import com.iremote.domain.InfraredDevice;
import com.iremote.domain.ZWaveDevice;

public class InfoChangeEvent {

	private String deviceid;
	private String name;
	private String loginname;
	private Integer status;
	private Integer gatewayonline;
	private List<ZWaveDevice> zwavedevice = new ArrayList<ZWaveDevice>();
	private List<InfraredDevice> infrareddevice = new ArrayList<InfraredDevice>();
	
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
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public List<ZWaveDevice> getZwavedevice() {
		return zwavedevice;
	}
	public void setZwavedevice(List<ZWaveDevice> zwavedevice) {
		this.zwavedevice = zwavedevice;
	}
	public List<InfraredDevice> getInfrareddevice() {
		return infrareddevice;
	}
	public void setInfrareddevice(List<InfraredDevice> infrareddevice) {
		this.infrareddevice = infrareddevice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getGatewayonline() {
		return gatewayonline;
	}

	public void setGatewayonline(Integer gatewayonline) {
		this.gatewayonline = gatewayonline;
	}
}
