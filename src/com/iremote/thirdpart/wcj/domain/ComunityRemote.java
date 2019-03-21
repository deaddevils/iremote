package com.iremote.thirdpart.wcj.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="wcj_comunityremote")
public class ComunityRemote {

	private int comunityremoteid;
	private String deviceid;
	private int comunityid ;
	private int thirdpartid;
	private int fix = 0 ;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "comunityremoteid")  
	public int getComunityremoteid() {
		return comunityremoteid;
	}
	public void setComunityremoteid(int comunityremoteid) {
		this.comunityremoteid = comunityremoteid;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public int getComunityid() {
		return comunityid;
	}
	public void setComunityid(int comunityid) {
		this.comunityid = comunityid;
	}
	public int getThirdpartid() {
		return thirdpartid;
	}
	public void setThirdpartid(int thirdpartid) {
		this.thirdpartid = thirdpartid;
	}
	public int getFix()
	{
		return fix;
	}
	public void setFix(int fix)
	{
		this.fix = fix;
	}
}
