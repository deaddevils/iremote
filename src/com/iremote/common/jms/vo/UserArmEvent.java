package com.iremote.common.jms.vo;

public class UserArmEvent 
{
	private int phoneuserid ;
	private String countrycode;
	private String phonenumber;
	private String armtype ;
	private int platform ;
	private int armstatus;
	
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public int getArmstatus() {
		return armstatus;
	}
	public void setArmstatus(int armstatus) {
		this.armstatus = armstatus;
	}
	public String getArmtype() {
		return armtype;
	}
	public void setArmtype(String armtype) {
		this.armtype = armtype;
	}

}
