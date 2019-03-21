package com.iremote.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="deviceoperationsteps")
public class DeviceOperationSteps 
{
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	private int deviceoprationstatusid ;
	private String deviceid;
	private Integer zwavedeviceid;
	private Integer infrareddeviceid;
	private Integer keyindex;
	private int optype;
	private Integer objid;
	private String appendmessage;
	private int status ;
	private Date starttime ;
	private Date expiretime ;
	private boolean finished;
	
	public int getDeviceoprationstatusid() {
		return deviceoprationstatusid;
	}
	public void setDeviceoprationstatusid(int deviceoprationstatusid) {
		this.deviceoprationstatusid = deviceoprationstatusid;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public Integer getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(Date expiretime) {
		this.expiretime = expiretime;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public String getAppendmessage() {
		return appendmessage;
	}
	public void setAppendmessage(String appendmessage) {
		this.appendmessage = appendmessage;
	}
	public int getOptype() {
		return optype;
	}
	public void setOptype(int optype) {
		this.optype = optype;
	}

	public Integer getInfrareddeviceid() {
		return infrareddeviceid;
	}
	public Integer getObjid() {
		return objid;
	}

	public void setInfrareddeviceid(Integer infrareddeviceid) {
		this.infrareddeviceid = infrareddeviceid;
	}
	public Integer getKeyindex() {
		return keyindex;
	}

	public void setKeyindex(Integer keyindex) {
		this.keyindex = keyindex;
	}

	public void setObjid(Integer objid) {
		this.objid = objid;
	}
}
