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
@Table(name="doorlockalarmphone")
public class Doorlockalarmphone {

	private int doorlockalarmphoneid;
	private DoorlockUser doorlockuser;
	private String countrycode;
	private String alarmphone;
	
	public String getAlarmphone() {
		return alarmphone;
	}
	public void setAlarmphone(String alarmphone) {
		this.alarmphone = alarmphone;
	}
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "doorlockalarmphoneid")  
	public int getDoorlockalarmphoneid() {
		return doorlockalarmphoneid;
	}
	public void setDoorlockalarmphoneid(int doorlockalarmphoneid) {
		this.doorlockalarmphoneid = doorlockalarmphoneid;
	}

	@JSON(serialize=false)
	@JSONField(serialize = false)
	@ManyToOne(targetEntity=DoorlockUser.class,cascade={CascadeType.DETACH})           
	@JoinColumn(name="doorlockuserid",referencedColumnName="doorlockuserid",nullable=false)
	public DoorlockUser getDoorlockuser() {
		return doorlockuser;
	}

	public void setDoorlockuser(DoorlockUser doorlockuser) {
		this.doorlockuser = doorlockuser;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public Doorlockalarmphone(DoorlockUser doorlockuser, String countrycode, String alarmphone) {
		super();
		this.doorlockuser = doorlockuser;
		this.countrycode = countrycode;
		this.alarmphone = alarmphone;
	}
	public Doorlockalarmphone(){
		
	}
}
