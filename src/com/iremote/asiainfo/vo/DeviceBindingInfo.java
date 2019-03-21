package com.iremote.asiainfo.vo;

public class DeviceBindingInfo {
	private String mainUserType;
	private String mainUserId;
	private String subUserType = "";
	private String subUserId = "" ;
	private String deviceId;
	private String type;

	public String getSubUserType() {
		return subUserType;
	}
	public void setSubUserType(String subUserType) {
		this.subUserType = subUserType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMainUserType() {
		return mainUserType;
	}
	public void setMainUserType(String mainUserType) {
		this.mainUserType = mainUserType;
	}
	public String getMainUserId() {
		return mainUserId;
	}
	public void setMainUserId(String mainUserId) {
		this.mainUserId = mainUserId;
	}
	public String getSubUserId() {
		return subUserId;
	}
	public void setSubUserId(String subUserId) {
		this.subUserId = subUserId;
	}


}
