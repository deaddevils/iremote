package com.iremote.thirdpart.wcj.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="wcj_comunity")
public class Comunity {

	private int comunityid ;
	private String name ;
	private String code ;
	private int thirdpartid;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "comunityid")  
	public int getComunityid() {
		return comunityid;
	}
	public void setComunityid(int comunityid) {
		this.comunityid = comunityid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getThirdpartid() {
		return thirdpartid;
	}
	public void setThirdpartid(int thirdpartid) {
		this.thirdpartid = thirdpartid;
	}
	
	
}
