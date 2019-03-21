package com.iremote.action.notification;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.NotificationSetting;
import com.iremote.domain.PhoneUser;
import com.iremote.service.NotificationSettingService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang.StringUtils;

public class SetNotificationSetting2Action {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	
	private Integer app;
	private Integer ring;
	private String mail;
	private PhoneUser phoneuser;

	public String execute()
	{
		if(app == null || ring == null){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		createNotificationSetting(IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION);
		createNotificationSetting(IRemoteConstantDefine.WARNING_SEND_TYPE_MAIL);
		return Action.SUCCESS;
	}


	private void createNotificationSetting(int type){
		NotificationSettingService svr = new NotificationSettingService();
		NotificationSetting n = svr.query(phoneuser.getPhoneuserid(), type);

		if ( n == null )
			n = new NotificationSetting();
		n.setNotificationtype(type);
		n.setPhonenumber(phoneuser.getPhonenumber());
		n.setPhoneuserid(phoneuser.getPhoneuserid());
		n.setApp(app);
		if(IRemoteConstantDefine.WARNING_SEND_TYPE_MAIL == type){
			n.setMail(mail);
		}else if(IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION == type){
			n.setRing(ring);
			n.ringToBuilderAndSound();
		}
		n.setStarttime("00:00");
		n.setEndtime("23:59");
		svr.saveorUpdate(n);
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setApp(Integer app) {
		this.app = app;
	}

	public void setRing(Integer ring) {
		this.ring = ring;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
