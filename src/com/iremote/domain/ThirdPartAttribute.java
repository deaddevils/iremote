package com.iremote.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="thirdpartattribute")
public class ThirdPartAttribute 
{
	private int thirdpartattributeid ;
	private int thirdpartid;
	private String code;
	private String value ;
	
	@Id 
	public int getThirdpartattributeid() {
		return thirdpartattributeid;
	}
	public void setThirdpartattributeid(int thirdpartattributeid) {
		this.thirdpartattributeid = thirdpartattributeid;
	}

	public int getThirdpartid() {
		return thirdpartid;
	}
	public void setThirdpartid(int thirdpartid) {
		this.thirdpartid = thirdpartid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
