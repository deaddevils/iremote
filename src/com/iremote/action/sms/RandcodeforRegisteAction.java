package com.iremote.action.sms;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.sms.SMSInterface;
import com.iremote.domain.Randcode;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RandcodeService;
import com.opensymphony.xwork2.Action;

public class RandcodeforRegisteAction {

	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber;
	private int platform;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private RandcodeService svr = new RandcodeService();
	private String language;
	
	public String execute()
	{
		if ( StringUtils.isNotBlank(this.phonenumber ))
			this.phonenumber = this.phonenumber.trim();
		if ( checkReqiste() == true )
		{
			resultCode = ErrorCodeDefine.USER_HAS_REGISTED;
			return Action.SUCCESS;
		}
		if ( checkRandcodeNumber() == false )
		{
			resultCode = ErrorCodeDefine.EXCEED_MAX_REQUEST_TIMES;
			return Action.SUCCESS;
		}
		
		String rc = RandCodeHelper.createRandCode();
		int result = SMSInterface.sendRandcode(countrycode ,phonenumber, rc , Utils.getUserLanguage(platform, language), platform);
		
		if ( result != 0 )
			resultCode = ErrorCodeDefine.SMS_SEND_FAILED;
		else 
		{
			Randcode r = new Randcode(rc ,countrycode ,phonenumber , 1);
			r.setPlatform(platform);
			svr.save(r);
		}
		
		return Action.SUCCESS;
	}
	
	private boolean checkReqiste()
	{
		PhoneUserService us = new PhoneUserService();
		if ( us.query(countrycode , phonenumber , platform) != null )
			return true;
		return false ;
	}
	
	private boolean checkRandcodeNumber()
	{
		List<Randcode> lst = svr.querybyphonenumber(countrycode , phonenumber , 1 , platform);
		if ( lst == null || lst.size() <= 10 )
			return true;
		return false ;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}
