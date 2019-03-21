package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="infreredcodeliberay")
public class InfreredCodeLiberay
{
	private int infreredcodeliberayid;
	private String devicetype;
	private int codeid;
	private String code;
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "infreredcodeliberayid")  
	public int getInfreredcodeliberayid()
	{
		return infreredcodeliberayid;
	}
	public void setInfreredcodeliberayid(int infreredcodeliberayid)
	{
		this.infreredcodeliberayid = infreredcodeliberayid;
	}
	public String getDevicetype()
	{
		return devicetype;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}
	public int getCodeid()
	{
		return codeid;
	}
	public void setCodeid(int codeid)
	{
		this.codeid = codeid;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	
}
