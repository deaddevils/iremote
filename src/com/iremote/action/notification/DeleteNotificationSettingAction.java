package com.iremote.action.notification;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.NotificationSetting;
import com.iremote.domain.PhoneUser;
import com.iremote.service.NotificationSettingService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class DeleteNotificationSettingAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	
	private int notificationtype;
	
	public String execute()
	{
		PhoneUser user = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		NotificationSettingService svr = new NotificationSettingService();
		NotificationSetting n = svr.query(user.getPhoneuserid(), notificationtype);
		
		if ( n != null )
			svr.delete(n);
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setNotificationtype(int notificationtype) {
		this.notificationtype = notificationtype;
	}
	
	
}
