package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fee_item")
public class FeeItem 
{
	private int feeitemid;
	private String name;
	private int feetype;
	private int objecttype;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "feeitemid")  
	public int getFeeitemid() {
		return feeitemid;
	}
	public void setFeeitemid(int feeitemid) {
		this.feeitemid = feeitemid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFeetype() {
		return feetype;
	}
	public void setFeetype(int feetype) {
		this.feetype = feetype;
	}
	public int getObjecttype() {
		return objecttype;
	}
	public void setObjecttype(int objecttype) {
		this.objecttype = objecttype;
	}

}
