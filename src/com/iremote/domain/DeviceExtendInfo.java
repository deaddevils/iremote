package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="deviceextendinfo")
public class DeviceExtendInfo
{
	private int deviceextendinfoid ;
	private int zwavedeviceid ;
	private String zwaveproductormessage;
	private String devicepassword;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "deviceextendinfoid")
	public int getDeviceextendinfoid()
	{
		return deviceextendinfoid;
	}
	public void setDeviceextendinfoid(int deviceextendinfoid)
	{
		this.deviceextendinfoid = deviceextendinfoid;
	}
	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	@Type(type="text") 
    @Column(name = "zwaveproductormessage") 
	public String getZwaveproductormessage()
	{
		return zwaveproductormessage;
	}
	public void setZwaveproductormessage(String zwaveproductormessage)
	{
		this.zwaveproductormessage = zwaveproductormessage;
	}
	public String getDevicepassword()
	{
		return devicepassword;
	}
	public void setDevicepassword(String devicepassword)
	{
		this.devicepassword = devicepassword;
	}
	
	
}
