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
@Table(name="pd_phoneuserproduction")
public class PdPhoneuserProduction 
{
	private int phoneuserproductionid;
	private int phoneuserid;
	private int productionid;
	private int status;
	private Date createtime;
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "phoneuserproductionid")  
	public int getPhoneuserproductionid() {
		return phoneuserproductionid;
	}
	public void setPhoneuserproductionid(int phoneuserproductionid) {
		this.phoneuserproductionid = phoneuserproductionid;
	}
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
	public int getProductionid() {
		return productionid;
	}
	public void setProductionid(int productionid) {
		this.productionid = productionid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	
}
