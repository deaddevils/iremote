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
@Table(name="phoneuser")
public class PhoneUser implements IUser{

	private int phoneuserid;
	private String countrycode;
	private String phonenumber;
	private String mail;
	private String name ;
	private String password;
	private Date createtime;
	private Date lastupdatetime;
	private String alias;
	private Integer longitude;
	private Integer latitude;
	private Integer smscount = 0;
	private Integer callcount = 0 ;
	private int platform ;
	private String openId ;
	private String token ;
	private int armstatus;
	private Integer familyid;
	private String language;
	private int usertype = 0 ;
    private Integer status;
    private String avatar;

	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "phoneuserid")  
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
	
    @Column(name = "phonenumber")  
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
    @Column(name = "password")  
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    @Column(name = "createtime")  
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
    @Column(name = "lastupdatetime")  
	public Date getLastupdatetime() {
		return lastupdatetime;
	}
	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}
    @Column(name = "alias")  
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	@Override
	public String getUsername() {
		return this.phonenumber;
	}
	
	public void setUsername(String username)
	{
		//do nothing
	}
	@Column(name = "longitude")  
	public Integer getLongitude() {
		return longitude;
	}
	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}
	@Column(name = "latitude")  
	public Integer getLatitude() {
		return latitude;
	}
	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}
	@Column(name = "smscount")  
	public Integer getSmscount() {
		return smscount;
	}
	public void setSmscount(Integer smscount) {
		this.smscount = smscount;
	}
	@Column(name = "callcount")  
	public Integer getCallcount() {
		return callcount;
	}
	public void setCallcount(Integer callcount) {
		this.callcount = callcount;
	}
	@Column(name = "platform")  
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getArmstatus() {
		return armstatus;
	}
	public void setArmstatus(int armstatus) {
		this.armstatus = armstatus;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public Integer getFamilyid() {
		return familyid;
	}
	public void setFamilyid(Integer familyid) {
		this.familyid = familyid;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
    
}
