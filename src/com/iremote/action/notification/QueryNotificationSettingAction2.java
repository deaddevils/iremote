package com.iremote.action.notification;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.NotificationSetting;
import com.iremote.domain.PhoneUser;
import com.iremote.service.NotificationSettingService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class QueryNotificationSettingAction2 {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private List<NotificationSetting> notificationsetting ;
	private int app = 0;
	private int ring = 1;
	private String mail;

	public String execute()
	{
		PhoneUser user = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		NotificationSettingService svr = new NotificationSettingService();
		notificationsetting = svr.query(user.getPhoneuserid());
		if(notificationsetting != null && notificationsetting.size() > 0){
			for(NotificationSetting ns : notificationsetting){
				if(ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_MAIL
						|| ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION){
					if(ns.getApp() != null){
						app = ns.getApp();
					}
					if(ns.getRing() != null){
						ring = ns.getRing();
					}
					if(StringUtils.isNotEmpty(ns.getMail())){
						mail = ns.getMail();
					}
				}
			}
		}


		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getApp() {
		return app;
	}

	public int getRing() {
		return ring;
	}

	public String getMail() {
		return mail;
	}
}
