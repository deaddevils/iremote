package com.iremote.vo;


import java.util.List;

import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;

public class RegistUserVO {
	private PhoneUser phoneuser;
	private List<Remote> devices;
	
	public List<Remote> getDevices() {
		return devices;
	}
	public void setDevices(List<Remote> devices) {
		this.devices = devices;
	}
	public PhoneUser getPhoneuser() {
		return phoneuser;
	}
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
}
