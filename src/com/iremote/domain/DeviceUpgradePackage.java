package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="deviceupgradepackage")
public class DeviceUpgradePackage
{
	private int deviceupgradepackageid;
	private String devicetype;
	private String productor;
	private String model;
	private String version1;
	private String version2;
	private String packagepath;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "deviceupgradepackageid")  
	public int getDeviceupgradepackageid()
	{
		return deviceupgradepackageid;
	}
	public void setDeviceupgradepackageid(int deviceupgradepackageid)
	{
		this.deviceupgradepackageid = deviceupgradepackageid;
	}
	public String getDevicetype()
	{
		return devicetype;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
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
	public String getVersion1()
	{
		return version1;
	}
	public void setVersion1(String version1)
	{
		this.version1 = version1;
	}
	public String getVersion2()
	{
		return version2;
	}
	public void setVersion2(String version2)
	{
		this.version2 = version2;
	}
	public String getPackagepath()
	{
		return packagepath;
	}
	public void setPackagepath(String packagepath)
	{
		this.packagepath = packagepath;
	}
	
	
}
