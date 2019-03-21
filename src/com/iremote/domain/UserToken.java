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
@Table(name="usertoken")
public class UserToken {
	private int tokenid;     
	private int phoneuserid;         
	private String token;
	private String securitytoken;
	private Date createtime = new Date();
	private Date lastupdatetime;
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "tokenid")  
	public int getTokenid() {
		return tokenid;
	}
	public void setTokenid(int tokenid) {
		this.tokenid = tokenid;
	}
    @Column(name = "phoneuserid")  
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
    @Column(name = "token")  
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    @Column(name = "createtime")  
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
    @Column(name = "lastupdatetime")  
	public Date getLastupdatetime() {
		return lastupdatetime;
	}
	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}
	public String getSecuritytoken() {
		return securitytoken;
	}
	public void setSecuritytoken(String securitytoken) {
		this.securitytoken = securitytoken;
	}
	
	
}
