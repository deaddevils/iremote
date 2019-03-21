package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="userinout")
public class UserInOut {

	private int userinoutid ;
	private String deviceid;
	private int phoneuserid;
	private String phonenumber;
	private int action ; //0:out , 1:in
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "userinoutid")  
	public int getUserinoutid() {
		return userinoutid;
	}
	public void setUserinoutid(int userinoutid) {
		this.userinoutid = userinoutid;
	}
    @Column(name = "deviceid")  
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
    @Column(name = "phoneuserid")  
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
    @Column(name = "phonenumber")  
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
    @Column(name = "action")  
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	
	
}
