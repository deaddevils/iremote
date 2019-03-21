package com.iremote.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="usershare")
public class UserShare 
{
	private int shareid;
	private int shareuserid;
	private String shareuser;
	private int touserid;
	private String tousercountrycode;
	private String touser;
	private int status ;
	private int sharetype;
	private Date createtime;
	private int sharedevicetype;

	private List<UserShareDevice> userShareDevices = new ArrayList<UserShareDevice>();
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "shareid")  
	public int getShareid() {
		return shareid;
	}
	public void setShareid(int shareid) {
		this.shareid = shareid;
	}
	
    @Column(name = "shareuser")  
	public String getShareuser() {
		return shareuser;
	}
	public void setShareuser(String shareuser) {
		this.shareuser = shareuser;
	}
    @Column(name = "touser")  
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
    @Column(name = "status")  
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    @Column(name = "createtime")  
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
    @Column(name = "shareuserid")  
	public int getShareuserid() {
		return shareuserid;
	}
	public void setShareuserid(int shareuserid) {
		this.shareuserid = shareuserid;
	}
    @Column(name = "touserid")  
	public int getTouserid() {
		return touserid;
	}
	public void setTouserid(int touserid) {
		this.touserid = touserid;
	}
	public int getSharetype() {
		return sharetype;
	}
	public void setSharetype(int sharetype) {
		this.sharetype = sharetype;
	}
	public String getTousercountrycode() {
		return tousercountrycode;
	}
	public void setTousercountrycode(String tousercountrycode) {
		this.tousercountrycode = tousercountrycode;
	}
	public int getSharedevicetype() {
		return sharedevicetype;
	}
	public void setSharedevicetype(int sharedevicetype) {
		this.sharedevicetype = sharedevicetype;
	}

	@OneToMany(targetEntity=UserShareDevice.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="userShare")
	public List<UserShareDevice> getUserShareDevices() {
		return userShareDevices;
	}
	public void setUserShareDevices(List<UserShareDevice> userShareDevices) {
		this.userShareDevices = userShareDevices;
	}
}
