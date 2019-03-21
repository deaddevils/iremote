package com.iremote.action.device.dsc;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.Action;

public class AppAddDSCAction {
	private String deviceid;

	public String execute(){
		if(StringUtils.isEmpty(deviceid)){
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

}
