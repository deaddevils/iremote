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
@Table(name="fee_paymentdetail")
public class FeePaymentDetail 
{
	private int paymentdetailid;
	private int transactionid;
	private int paymenttype;
	private int amount;
	private int maxdrawback;
	private int status;
	private int rfpaymentdetailid;
	private Date createtime;
	private Date payedtime;
	private Date expiretime;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "paymentdetailid")  
	public int getPaymentdetailid() {
		return paymentdetailid;
	}
	public void setPaymentdetailid(int paymentdetailid) {
		this.paymentdetailid = paymentdetailid;
	}
	public int getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}
	public int getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(int paymenttype) {
		this.paymenttype = paymenttype;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getRfpaymentdetailid() {
		return rfpaymentdetailid;
	}
	public void setRfpaymentdetailid(int rfpaymentdetailid) {
		this.rfpaymentdetailid = rfpaymentdetailid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getPayedtime() {
		return payedtime;
	}
	public void setPayedtime(Date payedtime) {
		this.payedtime = payedtime;
	}
	public Date getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(Date expiretime) {
		this.expiretime = expiretime;
	}

	
}
