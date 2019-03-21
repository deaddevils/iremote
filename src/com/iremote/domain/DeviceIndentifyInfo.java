package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="deviceindentifyinfo")
public class DeviceIndentifyInfo
{
	private int deviceindentifyinfoid ;
	private String devicetype;
	private String devicecode;
	private String qrcodeid;
	private String manufacturer;
	private String model;
	private Integer zwavedeviceid;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "deviceindentifyinfoid")
	public int getDeviceindentifyinfoid()
	{
		return deviceindentifyinfoid;
	}
	public void setDeviceindentifyinfoid(int deviceindentifyinfoid)
	{
		this.deviceindentifyinfoid = deviceindentifyinfoid;
	}
	public String getDevicetype()
	{
		return devicetype;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}
	public String getDevicecode()
	{
		return devicecode;
	}
	public void setDevicecode(String devicecode)
	{
		this.devicecode = devicecode;
	}
	public String getQrcodeid()
	{
		return qrcodeid;
	}
	public void setQrcodeid(String qrcodeid)
	{
		this.qrcodeid = qrcodeid;
	}
	public String getManufacturer()
	{
		return manufacturer;
	}
	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public Integer getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	public void setZwavedeviceid(Integer zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
}
