package com.iremote.action.phoneuser;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.opensymphony.xwork2.Action;

public class SetUserNameAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private PhoneUser phoneuser;
	private String name ;
	
	public String execute()
	{
		phoneuser.setName(name);
		
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
	public void setName(String name)
	{
		this.name = name;
	}
	
	
	
}
