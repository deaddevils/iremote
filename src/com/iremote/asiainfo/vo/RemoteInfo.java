package com.iremote.asiainfo.vo;

public class RemoteInfo {

	private String deviceId;
	private String deviceType;
	private String manufacturer;
	private String brand;
	private String model;
	private String authKey;
	private String version = "";
	private int type;
	private String createTime;
	private String ctcProductId = "";
	private DeviceDetail description = new DeviceDetail();

	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public DeviceDetail getDescription() {
		return description;
	}
	public void setDescription(DeviceDetail description) {
		this.description = description;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCtcProductId() {
		return ctcProductId;
	}
	public void setCtcProductId(String ctcProductId) {
		this.ctcProductId = ctcProductId;
	}

}
