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
@Table(name="smshistory")
public class SmsHistory {

	private int smshistoryid;
	private String countrycode;
	private String phonenumber ;
	private int phoneuserid;
	private String message ;
	private Date sendtime = new Date();
	
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "smshistoryid")  
	public int getSmshistoryid() {
		return smshistoryid;
	}
	public void setSmshistoryid(int smshistoryid) {
		this.smshistoryid = smshistoryid;
	}
    @Column(name = "phonenumber")  
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
    @Column(name = "phoneuserid")  
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
    @Column(name = "message")  
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    @Column(name = "sendtime")  
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	
	
}
