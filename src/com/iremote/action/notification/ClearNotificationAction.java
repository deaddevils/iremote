package com.iremote.action.notification;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.service.NotificationService;
import com.opensymphony.xwork2.Action;

public class ClearNotificationAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private PhoneUser phoneuser ;
	
	public String execute()
	{		
		NotificationService ns = new NotificationService();
		ns.clear(phoneuser.getPhoneuserid());
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
}
