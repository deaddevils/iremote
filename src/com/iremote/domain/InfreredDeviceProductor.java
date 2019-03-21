package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="infrereddeviceproductor")
public class InfreredDeviceProductor
{
	private int infrereddeviceproductorid;
	private String productor;
	private String name ;
	private String devicetype;
	private String codeids;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "infrereddeviceproductorid")  
	public int getInfrereddeviceproductorid()
	{
		return infrereddeviceproductorid;
	}
	public void setInfrereddeviceproductorid(int infrereddeviceproductorid)
	{
		this.infrereddeviceproductorid = infrereddeviceproductorid;
	}
	public String getProductor()
	{
		return productor;
	}
	public void setProductor(String productor)
	{
		this.productor = productor;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDevicetype()
	{
		return devicetype;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}
	public String getCodeids() {
		return codeids;
	}
	public void setCodeids(String codeids) {
		this.codeids = codeids;
	}
	
	
}
