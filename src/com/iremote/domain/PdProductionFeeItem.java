package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pd_productionfeeitem")
public class PdProductionFeeItem 
{
	private int productionfeeitemid;
	private int productionid;
	private int feeitemid;
	private String fullname;
	private int amount;
	private int maxdrawback;
	private int freezeamount;
	private int validamount;
	private int validunit;
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "productionfeeitemid")  
	public int getProductionfeeitemid() {
		return productionfeeitemid;
	}
	public void setProductionfeeitemid(int productionfeeitemid) {
		this.productionfeeitemid = productionfeeitemid;
	}
	public int getProductionid() {
		return productionid;
	}
	public void setProductionid(int productionid) {
		this.productionid = productionid;
	}
	public int getFeeitemid() {
		return feeitemid;
	}
	public void setFeeitemid(int feeitemid) {
		this.feeitemid = feeitemid;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getMaxdrawback() {
		return maxdrawback;
	}
	public void setMaxdrawback(int maxdrawback) {
		this.maxdrawback = maxdrawback;
	}
	public int getFreezeamount() {
		return freezeamount;
	}
	public void setFreezeamount(int freezeamount) {
		this.freezeamount = freezeamount;
	}
	public int getValidamount() {
		return validamount;
	}
	public void setValidamount(int validamount) {
		this.validamount = validamount;
	}
	public int getValidunit() {
		return validunit;
	}
	public void setValidunit(int validunit) {
		this.validunit = validunit;
	}

}
