package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="devicefunctionversioncapability")
public class DeviceFunctionVersionCapability
{
	private int devicefunctionversioncapabilityid ;
	private String devicetype;
	private String functionversion;
	private int capabilitycode;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "devicefunctionversioncapabilityid")  
	public int getDevicefunctionversioncapabilityid()
	{
		return devicefunctionversioncapabilityid;
	}
	public void setDevicefunctionversioncapabilityid(int devicefunctionversioncapabilityid)
	{
		this.devicefunctionversioncapabilityid = devicefunctionversioncapabilityid;
	}
	public String getDevicetype()
	{
		return devicetype;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}
	public String getFunctionversion()
	{
		return functionversion;
	}
	public void setFunctionversion(String functionversion)
	{
		this.functionversion = functionversion;
	}
	public int getCapabilitycode()
	{
		return capabilitycode;
	}
	public void setCapabilitycode(int capabilitycode)
	{
		this.capabilitycode = capabilitycode;
	}
	
	
}
