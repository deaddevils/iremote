package com.iremote.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tp_communityadministrator")
public class CommunityAdministrator {

	private int id ;
	private int thirdpartid;
	private int communityid ;
	private int phoneuserid;
	private String logicname;
	private Date createtime;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getThirdpartid() {
		return thirdpartid;
	}
	public void setThirdpartid(int thirdpartid) {
		this.thirdpartid = thirdpartid;
	}
	public int getCommunityid() {
		return communityid;
	}
	public void setCommunityid(int communityid) {
		this.communityid = communityid;
	}
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}

	public String getLogicname() {
		return logicname;
	}
	public void setLogicname(String logicname) {
		this.logicname = logicname;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
}
