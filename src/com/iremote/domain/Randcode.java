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
@Table(name="randcode")
public class Randcode {

	private static final int PHONE_EXPIRE_TIME = 5 ;
    private static final int MAIL_EXPIRE_TIME = 24 * 60 ;

    private int randcodeid ;
	private String randcode ;
	private String countrycode;
	private String phonenumber;
	private String mail;
	private int platform;
	private int type;   //1:phoneUserRegiste 2:phoneUserResetpassword   3:mailUserRegiste   4:mailUserResetpassword
	private Date createtime;
	private Date expiretime;
		
	public Randcode() {
		super();
	}
	
	public Randcode(String randcode,String countrycode , String phonenumber, int type) {
		super();
		this.randcode = randcode;
		this.countrycode = countrycode;
		this.phonenumber = phonenumber;
		this.type = type;
		createtime = new Date();
		expiretime = new Date();
		expiretime.setTime(createtime.getTime() + PHONE_EXPIRE_TIME * 60 * 1000 );
	}

	public Randcode(String randcode, String mail, int type) {
		super();
		this.randcode = randcode;
		this.mail = mail;
		this.type = type;
		createtime = new Date();
		expiretime = new Date();
		expiretime.setTime(createtime.getTime() + MAIL_EXPIRE_TIME * 60 * 1000 );
	}

	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "randcodeid")  
	public int getRandcodeid() {
		return randcodeid;
	}
	public void setRandcodeid(int randcodeid) {
		this.randcodeid = randcodeid;
	}
    @Column(name = "randcode")  
	public String getRandcode() {
		return randcode;
	}
	public void setRandcode(String randcode) {
		this.randcode = randcode;
	}
    @Column(name = "type")  
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
    @Column(name = "createtime")  
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
    @Column(name = "expiretime")  
	public Date getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(Date expiretime) {
		this.expiretime = expiretime;
	}
    @Column(name = "phonenumber")  
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
