package com.iremote.action.phoneuser;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.opensymphony.xwork2.Action;

public class SetUserLanguageAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private PhoneUser phoneuser;
	private String language ;
	
	public String execute()
	{
		phoneuser.setLanguage(language);
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
