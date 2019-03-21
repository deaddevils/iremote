package com.iremote.action.notification;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.NotificationSetting;
import com.iremote.domain.PhoneUser;
import com.iremote.service.NotificationSettingService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class SetNotificationSettingAction {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	
	private int notificationtype;
	private int athome;
	private String starttime;
	private String endtime;

	public String execute()
	{
		PhoneUser user = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		NotificationSettingService svr = new NotificationSettingService();
		NotificationSetting n = svr.query(user.getPhoneuserid(), notificationtype);
		
		if ( n == null )
			n = new NotificationSetting();
		n.setNotificationtype(notificationtype);
		n.setAthome(athome);
		n.setStarttime(starttime);
		n.setEndtime(endtime);
		n.setPhonenumber(user.getPhonenumber());
		n.setPhoneuserid(user.getPhoneuserid());
		
		svr.saveorUpdate(n);
		
		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setNotificationtype(int notificationtype) {
		this.notificationtype = notificationtype;
	}
	public void setAthome(int athome) {
		this.athome = athome;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
