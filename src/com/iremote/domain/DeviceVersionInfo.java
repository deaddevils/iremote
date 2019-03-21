package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="deviceversioninfo")
public class DeviceVersionInfo
{
	private int deviceversioninfoid;
	private String version;
	private String productor ;
	private String model ;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "deviceversioninfoid")  
	public int getDeviceversioninfoid()
	{
		return deviceversioninfoid;
	}
	public void setDeviceversioninfoid(int deviceversioninfoid)
	{
		this.deviceversioninfoid = deviceversioninfoid;
	}
	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}
	public String getProductor()
	{
		return productor;
	}
	public void setProductor(String productor)
	{
		this.productor = productor;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	
	
}
