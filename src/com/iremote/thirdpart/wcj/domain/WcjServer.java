package com.iremote.thirdpart.wcj.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wcj_server")
public class WcjServer {
	private int thirdpartid;
	private String serverurl;
	private String loginname;
	private String password;
	
	@Id    
	public int getThirdpartid() {
		return thirdpartid;
	}
	public void setThirdpartid(int thirdpartid) {
		this.thirdpartid = thirdpartid;
	}
	public String getServerurl() {
		return serverurl;
	}
	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
