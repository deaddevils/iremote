package com.iremote.vo;

import java.util.ArrayList;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Associationscene;
import com.iremote.domain.UserShare;
import com.iremote.domain.UserShareDevice;

public class Share {
	private int shareid;
	private String countrycode;
	private String phonenumber;
	private Integer direction;
	private Integer status ;
	private Integer type;
	private Integer sharedevicetype;
	
	private List<UserShareDevice> sharedevice = new ArrayList<UserShareDevice>();

	public Share()
	{
		super();
	}
	public Share(UserShare us)
	{
		this.setShareid(us.getShareid());
		this.setStatus(us.getStatus());
		this.setType(us.getSharetype());
		this.setSharedevicetype(us.getSharedevicetype());
		this.setSharedevice(us.getUserShareDevices());
	}
	
	public int getShareid() {
		return shareid;
	}
	public void setShareid(int shareid) {
		this.shareid = shareid;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSharedevicetype() {
		return sharedevicetype;
	}
	public void setSharedevicetype(Integer sharedevicetype) {
		this.sharedevicetype = sharedevicetype;
	}
	public List<UserShareDevice> getSharedevice() {
		return sharedevice;
	}
	public void setSharedevice(List<UserShareDevice> sharedevice) {
		this.sharedevice = sharedevice;
	}

	
}
