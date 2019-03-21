package com.iremote.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="thirdparttoken")
public class ThirdPartToken {
	private int thirdparttokenid;     
	private int thirdpartid;
	private String code;         
	private String token;         
	private Date createtime;
	private Date lastupdatetime;
	private Date validtime;
	
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "thirdparttokenid")  
	public int getThirdparttokenid() {
		return thirdparttokenid;
	}
	public void setThirdparttokenid(int thirdparttokenid) {
		this.thirdparttokenid = thirdparttokenid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getLastupdatetime() {
		return lastupdatetime;
	}
	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}
	public Date getValidtime() {
		return validtime;
	}
	public void setValidtime(Date validtime) {
		this.validtime = validtime;
	}
	public int getThirdpartid() {
		return thirdpartid;
	}
	public void setThirdpartid(int thirdpartid) {
		this.thirdpartid = thirdpartid;
	}

	
}
