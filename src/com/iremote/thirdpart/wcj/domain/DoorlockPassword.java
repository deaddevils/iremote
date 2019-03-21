package com.iremote.thirdpart.wcj.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="doorlockpassword")
@DynamicUpdate(true)
public class DoorlockPassword {

	private int doorlockpasswordid ;
	private int zwavedeviceid ;
	private int usertype = 1;
	private int usercode ;
	private String password;
	private Date validfrom;
	private Date validthrough;
	private int synstatus;
	private int status;
	private int errorcount = 0 ;
	private Date createtime = new Date();
	private Date sendtime ;
	private String phonenumber ;
	private int passwordtype = 1 ;
	private Integer weekday;
	private String starttime ;
	private String endtime;
	private String tid;
	private Integer locktype;
	private String username;
	
	public Integer getWeekday() {
		return weekday;
	}
	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "doorlockpasswordid")  
	public int getDoorlockpasswordid() {
		return doorlockpasswordid;
	}
	public void setDoorlockpasswordid(int doorlockpasswordid) {
		this.doorlockpasswordid = doorlockpasswordid;
	}
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public int getUsercode() {
		return usercode;
	}
	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getValidfrom() {
		return validfrom;
	}
	public void setValidfrom(Date validfrom) {
		this.validfrom = validfrom;
	}
	public Date getValidthrough() {
		return validthrough;
	}
	public void setValidthrough(Date validthrough) {
		this.validthrough = validthrough;
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
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public int getSynstatus() {
		return synstatus;
	}
	public void setSynstatus(int synstatus) {
		this.synstatus = synstatus;
	}
	public int getErrorcount() {
		return errorcount;
	}
	public void setErrorcount(int errorcount) {
		this.errorcount = errorcount;
	}
	public String getPhonenumber()
	{
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber)
	{
		this.phonenumber = phonenumber;
	}
	public int getPasswordtype()
	{
		return passwordtype;
	}
	public void setPasswordtype(int passwordtype)
	{
		this.passwordtype = passwordtype;
	}
	public int getUsertype()
	{
		return usertype;
	}
	public void setUsertype(int usertype)
	{
		this.usertype = usertype;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Integer getLocktype() {
		if (locktype == null) {
			return 0;
		}
		return locktype;
	}

	public void setLocktype(Integer locktype) {
		this.locktype = locktype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
