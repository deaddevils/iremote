package com.iremote.action.device.dsc;

import com.iremote.domain.PhoneUser;
import com.opensymphony.xwork2.Action;

public class AddDSCDevicePageAction
{
	private String deviceid ;
	private String name;
	private String password;
	private PhoneUser phoneuser;
	private int platform;
	public String execute()
	{
		platform = phoneuser.getPlatform();
		return Action.SUCCESS;
	}

	public String getDeviceid()
	{
		return deviceid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	
}
