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
@Table(name="address")
public class Address {
	private Integer addressid;
	private Integer phoneuserid;
	private Integer regionid;
	private Integer provinceid;
	private Integer cityid;
	private Date createtime;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "addressid")
	public Integer getAddressid() {
		return addressid;
	}
	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}
	public Integer getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(Integer phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
	public Integer getRegionid() {
		return regionid;
	}
	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}
	public Integer getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}
	public Integer getCityid() {
		return cityid;
	}
	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
