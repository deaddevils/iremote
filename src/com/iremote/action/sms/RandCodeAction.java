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
import com.opensymphony.xwork2.ActionContext;

public class RandCodeAction 
{
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber;
	private int platform;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private RandcodeService svr = new RandcodeService();
	private String language;
	private String authcode;
	private int type ;
	
	public String execute()
	{
		if ( StringUtils.isNotBlank(this.phonenumber ))
			this.phonenumber = this.phonenumber.trim();
		String ac = (String)ActionContext.getContext().getSession().get("rand");
		
		if ( authcode == null || !authcode.equalsIgnoreCase(ac))
		{
			resultCode = ErrorCodeDefine.AUTH_IMAGE_CODE_ERROR;
			return Action.SUCCESS;
		}
		
		if ( checkReqiste() == true  )
		{
			if ( type == IRemoteConstantDefine.SMS_RANDCODE_TYPE_REGIST)
			{
				resultCode = ErrorCodeDefine.USER_HAS_REGISTED;
				return Action.SUCCESS;
			}
		}
		else 
		{
			if ( type == IRemoteConstantDefine.SMS_RANDCODE_TYPE_RESET_PASSWORD)
			{
				resultCode = ErrorCodeDefine.USER_HAS_NOT_REGISTED;
				return Action.SUCCESS;
			}
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
			Randcode r = new Randcode(rc ,countrycode ,phonenumber , type);
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

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public void setType(int type) {
		this.type = type;
	}
}
