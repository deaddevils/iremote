package com.iremote.common.jms.vo;

import java.util.Date;

public class ZWaveDeviceUnalarmEvent extends ZWaveDeviceEvent {

	private String employeename ;
	private String phonenumber ;
	private String countrycode ;

	
	public ZWaveDeviceUnalarmEvent() {
		super();
	}

	public ZWaveDeviceUnalarmEvent(int zwavedeviceid, String deviceid, int nuid, String eventtype, Date eventtime) 
	{
		super(zwavedeviceid, deviceid, nuid, eventtype, eventtime, 0);
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

}
