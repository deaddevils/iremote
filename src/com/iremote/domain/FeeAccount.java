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
@Table(name="fee_account")
public class FeeAccount 
{
	private int accountid;
	private int phoneuserid;
	private int balance;
	private int blockedbalance;
	private int giftbalance;
	private int freesmscount;
	private int giftsmscount;
	private int payedsmscount;
	private Date createtime;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "accountid")  
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getBlockedbalance() {
		return blockedbalance;
	}
	public void setBlockedbalance(int blockedbalance) {
		this.blockedbalance = blockedbalance;
	}
	public int getGiftbalance() {
		return giftbalance;
	}
	public void setGiftbalance(int giftbalance) {
		this.giftbalance = giftbalance;
	}
	public int getFreesmscount() {
		return freesmscount;
	}
	public void setFreesmscount(int freesmscount) {
		this.freesmscount = freesmscount;
	}
	public int getGiftsmscount() {
		return giftsmscount;
	}
	public void setGiftsmscount(int giftsmscount) {
		this.giftsmscount = giftsmscount;
	}
	public int getPayedsmscount() {
		return payedsmscount;
	}
	public void setPayedsmscount(int payedsmscount) {
		this.payedsmscount = payedsmscount;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	
}
