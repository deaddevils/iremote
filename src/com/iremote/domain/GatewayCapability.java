package com.iremote.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="gatewaycapability")
public class GatewayCapability
{
	private int gatewaycapabilityid ;
	private Remote remote;
	private int capabilitycode;
	private String capabilityvalue;

	public GatewayCapability()
	{
		super();
	}
	
	public GatewayCapability(Remote remote, int capabilitycode)
	{
		super();
		this.remote = remote;
		this.capabilitycode = capabilitycode;
	}

	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "gatewaycapabilityid")  
	public int getGatewaycapabilityid()
	{
		return gatewaycapabilityid;
	}
	public void setGatewaycapabilityid(int gatewaycapabilityid)
	{
		this.gatewaycapabilityid = gatewaycapabilityid;
	}

	public int getCapabilitycode()
	{
		return capabilitycode;
	}
	public void setCapabilitycode(int capabilitycode)
	{
		this.capabilitycode = capabilitycode;
	}

	@JSONField(serialize = false)
	@JSON(serialize=false)
	@ManyToOne(targetEntity=Remote.class,cascade={CascadeType.DETACH})           
	@JoinColumn(name="deviceid",referencedColumnName="deviceid",nullable=true)
	public Remote getRemote()
	{
		return remote;
	}

	public void setRemote(Remote remote)
	{
		this.remote = remote;
	}

	public String getCapabilityvalue() {
		return capabilityvalue;
	}

	public void setCapabilityvalue(String capabilityvalue) {
		this.capabilityvalue = capabilityvalue;
	}
}
