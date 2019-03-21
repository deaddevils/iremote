package com.iremote.asiainfo.vo;

import com.alibaba.fastjson.JSONArray;

public class CommandInfo {

	private String deviceId;
	private String userType = "1";
	private String operUser ;
	private JSONArray controlList = new JSONArray() ;

	public CommandInfo() {
		super();
	}
	public CommandInfo(String deviceId, String userType, String operUser) {
		super();
		this.deviceId = deviceId;
		this.userType = userType;
		this.operUser = operUser;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getOperUser() {
		return operUser;
	}
	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}
	public JSONArray getControlList() {
		return controlList;
	}
	public void setControlList(JSONArray controlList) {
		this.controlList = controlList;
	}
	
	
}
