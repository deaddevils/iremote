package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="gatewayinfo")
public class GatewayInfo
{
	private int gatewayinfoid;
	private String deviceid;
	private String qrcodekey ;
	private String gatewaytype;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "gatewayinfoid")  
	public int getGatewayinfoid()
	{
		return gatewayinfoid;
	}
	
	public void setGatewayinfoid(int gatewayinfoid)
	{
		this.gatewayinfoid = gatewayinfoid;
	}
	public String getDeviceid()
	{
		return deviceid;
	}
	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}
	public String getQrcodekey()
	{
		return qrcodekey;
	}
	public void setQrcodekey(String qrcodekey)
	{
		this.qrcodekey = qrcodekey;
	}

	public String getGatewaytype()
	{
		return gatewaytype;
	}

	public void setGatewaytype(String gatewaytype)
	{
		this.gatewaytype = gatewaytype;
	}
}
