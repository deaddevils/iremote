package com.iremote.action.phoneuser;

import java.util.Date;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.Address;
import com.iremote.domain.PhoneUser;
import com.iremote.service.AddressService;
import com.opensymphony.xwork2.Action;

public class SetUserInfoAction {
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private PhoneUser phoneuser;
	private String name;
	private Integer regionid;
	private Integer provinceid;
	private Integer cityid;

	public String execute() {
		phoneuser.setName(name);
		
		AddressService as = new AddressService();
		int phoneuserid = phoneuser.getPhoneuserid();
		Address useraddress = as.queryByPhoneuserid(phoneuserid);
		if(useraddress!=null){
			useraddress.setRegionid(regionid);
			useraddress.setProvinceid(provinceid);
			useraddress.setCityid(cityid);
		}else{
			useraddress = new Address();
			useraddress.setPhoneuserid(phoneuser.getPhoneuserid());
			useraddress.setRegionid(regionid);
			useraddress.setProvinceid(provinceid);
			useraddress.setCityid(cityid);
			useraddress.setCreatetime(new Date());
		}
		as.saveOrUpdate(useraddress);
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}



}
