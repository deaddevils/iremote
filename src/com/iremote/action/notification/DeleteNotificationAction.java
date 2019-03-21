package com.iremote.action.notification;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.notification.INotification;
import com.iremote.service.NotificationService;
import com.opensymphony.xwork2.Action;

public class DeleteNotificationAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int notificationid;
	private PhoneUser phoneuser ;
	
	public String execute()
	{		
		NotificationService svr = new NotificationService();
	
		Notification n = svr.query(notificationid);
		if ( n == null )
		{
			resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( n.getPhoneuserid() != null && n.getPhoneuserid() != phoneuser.getPhoneuserid() )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}

		n.setDeleteflag(1);
		n.setDeletephoneuserid(phoneuser.getPhoneuserid());
		
		INotification nt = svr.queryByDeviceType(notificationid, n.getMajortype(), n.getDevicetype());
		if ( nt == null )
			return Action.SUCCESS;
		
		nt.setDeleteflag(1);
		nt.setDeletephoneuserid(phoneuser.getPhoneuserid());
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setNotificationid(int notificationid) {
		this.notificationid = notificationid;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

}
