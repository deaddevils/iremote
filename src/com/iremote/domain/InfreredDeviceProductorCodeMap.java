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
@Table(name="infrereddeviceproductorcodemap")
public class InfreredDeviceProductorCodeMap
{
	private int infrereddeviceproductorcodemapid;
	private String productor;
	private String devicetype;
	private String codeids;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "infrereddeviceproductorcodemapid")  
	public int getInfrereddeviceproductorcodemapid()
	{
		return infrereddeviceproductorcodemapid;
	}
	public void setInfrereddeviceproductorcodemapid(int infrereddeviceproductorcodemapid)
	{
		this.infrereddeviceproductorcodemapid = infrereddeviceproductorcodemapid;
	}
	public String getProductor()
	{
		return productor;
	}
	public void setProductor(String productor)
	{
		this.productor = productor;
	}
	public String getDevicetype()
	{
		return devicetype;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}
	@Type(type="text")  
	@Column(name = "codeids")  
	public String getCodeids()
	{
		return codeids;
	}
	public void setCodeids(String codeids)
	{
		this.codeids = codeids;
	}

	
}
