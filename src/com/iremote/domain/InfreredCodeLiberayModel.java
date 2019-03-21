package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="infreredcodeliberaymodel")
public class InfreredCodeLiberayModel 
{
	private int infreredcodeliberaymodelid ;
	private String productor;
	private String devicetype;
	private String model;
	private int codeid;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "infreredcodeliberaymodelid")  
	public int getInfreredcodeliberaymodelid() {
		return infreredcodeliberaymodelid;
	}
	public void setInfreredcodeliberaymodelid(int infreredcodeliberaymodelid) {
		this.infreredcodeliberaymodelid = infreredcodeliberaymodelid;
	}
	public String getProductor() {
		return productor;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getCodeid() {
		return codeid;
	}
	public void setCodeid(int codeid) {
		this.codeid = codeid;
	}
	
	
}
