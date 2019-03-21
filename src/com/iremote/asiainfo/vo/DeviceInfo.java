package com.iremote.asiainfo.vo;

public class DeviceInfo {
	
	private String deviceId;
	private String deviceCategories;
	private String deviceClass;
	private String manufacturer;
	private String brand;
	private String model;
	private String version = "";
	private String createTime;
	private String deviceName;
	private String equipmentTag;
	private DeviceDetail description = new DeviceDetail();
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceCategories() {
		return deviceCategories;
	}
	public void setDeviceCategories(String deviceCategories) {
		this.deviceCategories = deviceCategories;
	}
	public String getDeviceClass() {
		return deviceClass;
	}
	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public DeviceDetail getDescription() {
		return description;
	}
	public void setDescription(DeviceDetail description) {
		this.description = description;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getEquipmentTag() {
		return equipmentTag;
	}
	public void setEquipmentTag(String equipmentTag) {
		this.equipmentTag = equipmentTag;
	}
	
}
