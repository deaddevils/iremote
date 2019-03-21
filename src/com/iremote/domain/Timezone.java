package com.iremote.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="timezone")
public class Timezone {
	
	private int timezoneid;
	private String zonetext;
	private String zoneid; //+8
	private String id ;  //Asia/Shanghai
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	public int getTimezoneid() {
		return timezoneid;
	}
	public void setTimezoneid(int timezoneid) {
		this.timezoneid = timezoneid;
	}
	public String getZonetext() {
		return zonetext;
	}
	public void setZonetext(String zonetext) {
		this.zonetext = zonetext;
	}
	public String getZoneid() {
		return zoneid;
	}
	public void setZoneid(String zoneid) {
		this.zoneid = zoneid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
