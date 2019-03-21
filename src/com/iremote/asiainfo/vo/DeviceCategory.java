package com.iremote.asiainfo.vo;

public class DeviceCategory {

	private String model;
	private String deviceCategories;
	private String deviceClass;

	public DeviceCategory(String model, String deviceCategories,
			String deviceClass) {
		super();
		this.model = model;
		this.deviceCategories = deviceCategories;
		this.deviceClass = deviceClass;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
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

}
