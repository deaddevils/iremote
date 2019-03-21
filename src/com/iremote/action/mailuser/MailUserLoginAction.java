package com.iremote.action.mailuser;

import com.iremote.action.phoneuser.PhoneUserLoginAction;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.domain.Family;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.PhoneUserAttribute;
import com.iremote.domain.UserToken;
import com.iremote.interceptor.SessionInterceptor;
import com.iremote.service.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MailUserLoginAction extends PhoneUserLoginAction{

	private String mail;

	@Override
	public void getPhoneUser() {
		user = us.query(mail, IRemoteConstantDefine.USER_STATUS_ENABLED , platform);
	}

	@Override
	protected int checkpassword() {
		if ( password == null
				|| !svr.checkPassword(user.getMail() , password, user.getPassword()))
		{
			resultCode = ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG;
		}
		return resultCode;
	}

	public void setMail(String mail) {
        this.mail = mail;
    }
}
