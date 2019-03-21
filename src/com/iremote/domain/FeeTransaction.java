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
@Table(name="fee_transaction")
public class FeeTransaction 
{
	private int transactionid;
	private int accountid;
	private int transactiontype;
	private int price;
	private int recharge;
	private int gift;
	private int deduct;
	private int freezeamount;
	private int freezegift;
	private int prebalance;
	private int postbalance;
	private int pregift;
	private int postgift;
	private int prefreeze;
	private int postfreeze;
	private int maxdrawback;
	private Date createtime;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "transactionid")  
	public int getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public int getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(int transactiontype) {
		this.transactiontype = transactiontype;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getRecharge() {
		return recharge;
	}
	public void setRecharge(int recharge) {
		this.recharge = recharge;
	}
	public int getGift() {
		return gift;
	}
	public void setGift(int gift) {
		this.gift = gift;
	}
	public int getDeduct() {
		return deduct;
	}
	public void setDeduct(int deduct) {
		this.deduct = deduct;
	}
	public int getFreezeamount() {
		return freezeamount;
	}
	public void setFreezeamount(int freezeamount) {
		this.freezeamount = freezeamount;
	}
	public int getFreezegift() {
		return freezegift;
	}
	public void setFreezegift(int freezegift) {
		this.freezegift = freezegift;
	}
	public int getPrebalance() {
		return prebalance;
	}
	public void setPrebalance(int prebalance) {
		this.prebalance = prebalance;
	}
	public int getPostbalance() {
		return postbalance;
	}
	public void setPostbalance(int postbalance) {
		this.postbalance = postbalance;
	}
	public int getPregift() {
		return pregift;
	}
	public void setPregift(int pregift) {
		this.pregift = pregift;
	}
	public int getPostgift() {
		return postgift;
	}
	public void setPostgift(int postgift) {
		this.postgift = postgift;
	}
	public int getPrefreeze() {
		return prefreeze;
	}
	public void setPrefreeze(int prefreeze) {
		this.prefreeze = prefreeze;
	}
	public int getPostfreeze() {
		return postfreeze;
	}
	public void setPostfreeze(int postfreeze) {
		this.postfreeze = postfreeze;
	}
	public int getMaxdrawback() {
		return maxdrawback;
	}
	public void setMaxdrawback(int maxdrawback) {
		this.maxdrawback = maxdrawback;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	

}
