package com.iremote.action.phoneuser;

import org.apache.commons.lang.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.AdvertBanner;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.PhoneUserAttribute;
import com.iremote.domain.ThirdPart;
import com.iremote.service.AdvertBannerService;
import com.iremote.service.PhoneUserAttributeService;
import com.iremote.service.PhoneUserService;
import com.opensymphony.xwork2.Action;

public class SetAvertBannerAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private ThirdPart thirdpart;
	private int phoneuserid;
	private String phonenumber;
	private int advertbannerid;
	private String hometitle;
	
	public String execute(){
		PhoneUserService pus = new PhoneUserService();
		PhoneUser user = null;
		if(phoneuserid==0&&StringUtils.isBlank(phonenumber)){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		if(phoneuserid!=0){
			user = pus.query(phoneuserid);
			if(user.getPlatform()!=thirdpart.getPlatform()){
				resultCode = ErrorCodeDefine.NO_PRIVILEGE;
				return Action.SUCCESS;
			}
		}else{
			user = pus.query(phonenumber, thirdpart.getPlatform());
		}
		if(user==null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXIST;
			return Action.SUCCESS;
		}
		AdvertBannerService abs = new AdvertBannerService();
		AdvertBanner advertbanner = abs.queryById(advertbannerid);
		PhoneUserAttributeService puas = new PhoneUserAttributeService();
		if(advertbanner!=null){
			PhoneUserAttribute userattribute = puas.querybyphoneuseridandcode(user.getPhoneuserid(), "advertbannerid");
			if(userattribute!=null){
				userattribute.setValue(String.valueOf(advertbannerid));
			}else{
				userattribute = new PhoneUserAttribute();
				userattribute.setPhoneuserid(user.getPhoneuserid());
				userattribute.setCode("advertbannerid");
				userattribute.setValue(String.valueOf(advertbannerid));
			}
			puas.saveOrUpdate(userattribute);
		}
		PhoneUserAttribute usertitle = puas.querybyphoneuseridandcode(user.getPhoneuserid(), "hometitle");
		if(usertitle!=null){
			usertitle.setValue(hometitle);
		}else{
			usertitle = new PhoneUserAttribute();
			usertitle.setPhoneuserid(user.getPhoneuserid());
			usertitle.setCode("hometitle");
			usertitle.setValue(hometitle);

		}
		puas.saveOrUpdate(usertitle);
		return Action.SUCCESS;
	}
	
	public void setThirdpart(ThirdPart thirdpart) {
		this.thirdpart = thirdpart;
	}

	public int getResultCode() {
		return resultCode;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public void setAdvertbannerid(int advertbannerid) {
		this.advertbannerid = advertbannerid;
	}

	public void setHometitle(String hometitle) {
		this.hometitle = hometitle;
	}
	
}
