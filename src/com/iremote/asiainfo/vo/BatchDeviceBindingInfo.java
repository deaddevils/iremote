package com.iremote.asiainfo.vo;

import java.util.List;

public class BatchDeviceBindingInfo {

	private String mainUserType;
	private String mainUserId;
	private String subUserType = "";
	private String subUserId = "" ;
	private List<String> deviceIdList;
	private String type;
	
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
	public String getSubUserType() {
		return subUserType;
	}
	public void setSubUserType(String subUserType) {
		this.subUserType = subUserType;
	}
	public String getSubUserId() {
		return subUserId;
	}
	public void setSubUserId(String subUserId) {
		this.subUserId = subUserId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getDeviceIdList() {
		return deviceIdList;
	}
	public void setDeviceIdList(List<String> deviceIdList) {
		this.deviceIdList = deviceIdList;
	}


}
