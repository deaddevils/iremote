package com.iremote.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.iremote.common.Utils;

@Entity
@Table(name="doorlockuser")
public class DoorlockUser {

	private int doorlockuserid;
	private int zwavedeviceid;
	private int usertype; //21: password , 32 card , 22 fingerprint
	private int usercode;
	private String username ;
	private int cardid ;
	private Date validfrom;
	private Date validthrough;
	private Integer weekday;
	private String starttime ;
	private String endtime;
	private int alarmtype;
	private List<Doorlockalarmphone> doorlockalarmphone = new ArrayList<Doorlockalarmphone>();
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "doorlockuserid")  
	public int getDoorlockuserid() {
		return doorlockuserid;
	}
	public void setDoorlockuserid(int doorlockuserid) {
		this.doorlockuserid = doorlockuserid;
	}
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public int getUsercode() {
		return usercode;
	}
	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	public int getCardid()
	{
		return cardid;
	}
	public void setCardid(int cardid)
	{
		this.cardid = cardid;
	}
	public Date getValidfrom() {
		return validfrom;
	}
	public void setValidfrom(Date validfrom) {
		this.validfrom = validfrom;
	}
	public Date getValidthrough() {
		return validthrough;
	}
	
	public void setValidthrough(Date validthrough) {
		
		this.validthrough = validthrough;
	}
	
	public Integer getWeekday() {
		return weekday;
	}
	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public void setValidthrough(String validthrough) {
		if ( StringUtils.isBlank(validthrough))
			this.validthrough = null; 
		else if ( validthrough.length() == "yyyy-MM-dd HH:mm".length())
			this.validthrough = Utils.parseMin(validthrough);
		else 
			this.validthrough = Utils.parseTime(validthrough);
	}
	
	public void setValidfrom(String validfrom) {
		if ( StringUtils.isBlank(validfrom))
			this.validfrom = null ;
		else if ( validfrom.length() == "yyyy-MM-dd HH:mm".length())
			this.validfrom = Utils.parseMin(validfrom);
		else
			this.validfrom = Utils.parseTime(validfrom);
	}
	public int getAlarmtype() {
		return alarmtype;
	}
	public void setAlarmtype(int alarmtype) {
		this.alarmtype = alarmtype;
	}
	@OneToMany(targetEntity=Doorlockalarmphone.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="doorlockuser")   
	public List<Doorlockalarmphone> getDoorlockalarmphone() {
		return doorlockalarmphone;
	}
	public void setDoorlockalarmphone(List<Doorlockalarmphone> doorlockalarmphone) {
		this.doorlockalarmphone = doorlockalarmphone;
	}
	
}
