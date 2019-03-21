package com.iremote.vo;

public class IremoteIP {

	private String deviceid;
	private String ssid ;
	private String ip ;
	private String mac ;
	private int status;
	private String version;
	private int iversion;
	private int network;
	private int networkintensity;
	private int powertype;
	private Integer battery;
	private String temperature;
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public synchronized int getStatus() {
		return status;
	}
	public synchronized void setStatus(int status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getIversion() {
		return iversion;
	}
	public void setIversion(int iversion) {
		this.iversion = iversion;
	}
	public int getNetwork() {
		return network;
	}
	public void setNetwork(int network) {
		this.network = network;
	}
	public int getNetworkintensity() {
		return networkintensity;
	}
	public void setNetworkintensity(int networkintensity) {
		this.networkintensity = networkintensity;
	}
	public int getPowertype() {
		return powertype;
	}
	public void setPowertype(int powertype) {
		this.powertype = powertype;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	
}
