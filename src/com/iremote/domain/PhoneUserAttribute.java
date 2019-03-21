package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="phoneuserattribute")
public class PhoneUserAttribute
{
	private int phoneuserattributeid;
	private int phoneuserid ;
	private String code;
	private String value ;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "generator", strategy = "increment")
	public int getPhoneuserattributeid()
	{
		return phoneuserattributeid;
	}
	public void setPhoneuserattributeid(int phoneuserattributeid)
	{
		this.phoneuserattributeid = phoneuserattributeid;
	}
	public int getPhoneuserid()
	{
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid)
	{
		this.phoneuserid = phoneuserid;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	
	
}
