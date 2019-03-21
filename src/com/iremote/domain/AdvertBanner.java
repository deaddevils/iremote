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
@Table(name="advertbanner")
public class AdvertBanner {

	private Integer advertbannerid;
	private String name;
	private Integer thirdpartid;
	private Integer isdefault;
	private String description;
	private Date createtime;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "advertbannerid")  
	public Integer getAdvertbannerid() {
		return advertbannerid;
	}
	public void setAdvertbannerid(Integer advertbannerid) {
		this.advertbannerid = advertbannerid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getThirdpartid() {
		return thirdpartid;
	}
	public void setThirdpartid(Integer thirdpartid) {
		this.thirdpartid = thirdpartid;
	}
	public Integer getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
