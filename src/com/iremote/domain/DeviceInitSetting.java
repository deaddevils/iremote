package com.iremote.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="deviceinitsetting")
public class DeviceInitSetting 
{
	private int deviceinitsettingid ;
	private String mid;
	private String manufacture;
	private String devicetype;
	private String initcmds;
	
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	public int getDeviceinitsettingid() {
		return deviceinitsettingid;
	}
	public void setDeviceinitsettingid(int deviceinitsettingid) {
		this.deviceinitsettingid = deviceinitsettingid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getManufacture() {
		return manufacture;
	}
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getInitcmds() {
		return initcmds;
	}
	public void setInitcmds(String initcmds) {
		this.initcmds = initcmds;
	}
}
