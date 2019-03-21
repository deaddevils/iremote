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
@Table(name="doorlockassociation")
public class DoorlockAssociation {

	private Integer doorlockassociationid;
	private Integer zwavedeviceid;
	private Integer usercode;
	private Integer objtype;
	private Integer objid;
	private String appendmessage;
	private Date creattime;
	
	public DoorlockAssociation() {
		super();
	}
	
	public DoorlockAssociation(Integer zwavedeviceid, Integer usercode, Integer objtype, Integer objid) {
		super();
		this.zwavedeviceid = zwavedeviceid;
		this.usercode = usercode;
		this.objtype = objtype;
		this.objid = objid;
	}

	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "doorlockassociationid")
	public Integer getDoorlockassociationid() {
		return doorlockassociationid;
	}
	public void setDoorlockassociationid(Integer doorlockassociationid) {
		this.doorlockassociationid = doorlockassociationid;
	}
	public Integer getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public Integer getUsercode() {
		return usercode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	public Integer getObjtype() {
		return objtype;
	}
	public void setObjtype(Integer objtype) {
		this.objtype = objtype;
	}
	public Integer getObjid() {
		return objid;
	}
	public void setObjid(Integer objid) {
		this.objid = objid;
	}
	public String getAppendmessage() {
		return appendmessage;
	}
	public void setAppendmessage(String appendmessage) {
		this.appendmessage = appendmessage;
	}
	public Date getCreattime() {
		return creattime;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	
	
}
