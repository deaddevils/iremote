package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="infrereddeviceproductormap")
public class InfreredDeviceProductorMap
{
	private int infrereddeviceproductormapid;
	private String productor;
	private String productormap ;
	private String devicetype;

	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "infrereddeviceproductormapid")  
	public int getInfrereddeviceproductormapid()
	{
		return infrereddeviceproductormapid;
	}
	public void setInfrereddeviceproductormapid(int infrereddeviceproductormapid)
	{
		this.infrereddeviceproductormapid = infrereddeviceproductormapid;
	}
	public String getProductor()
	{
		return productor;
	}
	public void setProductor(String productor)
	{
		this.productor = productor;
	}
	public String getProductormap()
	{
		return productormap;
	}
	public void setProductormap(String productormap)
	{
		this.productormap = productormap;
	}
	public String getDevicetype()
	{
		return devicetype;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}
	
	
}
