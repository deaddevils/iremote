package com.iremote.action.notification;

import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.NotificationSetting;
import com.iremote.domain.PhoneUser;
import com.iremote.service.NotificationSettingService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class QueryNotificationSettingAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private List<NotificationSetting> notificationsetting ;
	
	public String execute()
	{
		PhoneUser user = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		NotificationSettingService svr = new NotificationSettingService();
		notificationsetting = svr.query(user.getPhoneuserid());
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public List<NotificationSetting> getNotificationsetting() {
		return notificationsetting;
	}
	
	
}
