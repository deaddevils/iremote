package com.iremote.action.notification;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.service.NotificationService;
import com.opensymphony.xwork2.Action;

public class QueryNotificationAction {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String day;
	private String majortype = IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE;
	private String devicetype;
	private int page = 1 ;
	private List<Notification> notifications;
	private PhoneUser phoneuser;
	
	public String execute()
	{	
		Date s = null ;
		
		if ( day != null && day.length() > 0 )
		{
			s =  Utils.parseDay(day);
			Calendar e = Calendar.getInstance();
			e.setTime(s);
			e.add(Calendar.DAY_OF_MONTH, 1);
			s = e.getTime();
		}
		
		NotificationService svr = new NotificationService();
		notifications = svr.querybyreportime(s, majortype , devicetype, page  , phoneuser.getPhoneuserid());

		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public synchronized void setPage(int page) {
		this.page = page;
	}

	public synchronized void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public void setMajortype(String majortype) {
		this.majortype = majortype;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

}
