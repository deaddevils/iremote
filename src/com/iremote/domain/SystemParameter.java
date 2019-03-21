package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="systemparameter")
public class SystemParameter {
	
	private String key;
	private String strvalue;
	private Integer intvalue;
	@Id    
	@GenericGenerator(name = "generator", strategy = "assigned")   
    @Column(name = "\"key\"")  
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
    @Column(name = "strvalue")  
	public String getStrvalue() {
		return strvalue;
	}
	public void setStrvalue(String strvalue) {
		this.strvalue = strvalue;
	}
    @Column(name = "intvalue")  
	public Integer getIntvalue() {
		return intvalue;
	}
	public void setIntvalue(Integer intvalue) {
		this.intvalue = intvalue;
	}
	
	
}
