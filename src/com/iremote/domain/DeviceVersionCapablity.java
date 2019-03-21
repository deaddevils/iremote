package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="deviceversioncapablity")
public class DeviceVersionCapablity
{
	private int deviceversioncapabilityid ;
	private int deviceversioninfoid ;
	private int capabilitycode;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "deviceversioncapabilityid")  
	public int getDeviceversioncapabilityid()
	{
		return deviceversioncapabilityid;
	}
	public void setDeviceversioncapabilityid(int deviceversioncapabilityid)
	{
		this.deviceversioncapabilityid = deviceversioncapabilityid;
	}
	public int getCapabilitycode()
	{
		return capabilitycode;
	}
	public void setCapabilitycode(int capabilitycode)
	{
		this.capabilitycode = capabilitycode;
	}
	public int getDeviceversioninfoid()
	{
		return deviceversioninfoid;
	}
	public void setDeviceversioninfoid(int deviceversioninfoid)
	{
		this.deviceversioninfoid = deviceversioninfoid;
	}
}
