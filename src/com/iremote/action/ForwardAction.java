package com.iremote.action;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserAttributeService;
import com.opensymphony.xwork2.Action;

public class ForwardAction
{
	private PhoneUser phoneuser ;
	
	public String execute()
	{
			return Action.SUCCESS;
	}

	public PhoneUser getPhoneuser() {
		return phoneuser;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
	public boolean isAdminUser()
	{
		if ( phoneuser == null )
			return false ;
		
		PhoneUserAttributeService puas = new PhoneUserAttributeService();
	
		return PhoneUserHelper.isAdminUser(puas.querybyphoneuserid(phoneuser.getPhoneuserid()));
	}
	
	public boolean isAmetaAdminUser(){
		if ( phoneuser == null )
			return false ;
		
		PhoneUserAttributeService puas = new PhoneUserAttributeService();
		return PhoneUserHelper.isAmetaAdminUser(puas.querybyphoneuserid(phoneuser.getPhoneuserid()));
	
	}
}
