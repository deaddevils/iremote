package com.iremote.action.mailuser;

import com.iremote.action.sms.RandCodeHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;

public class MailUserResetPasswordPageAction {
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int platform;
	private String mail;
	private String randcode;
	private PhoneUserService us = new PhoneUserService();
	private UserService svr = new UserService();
	private PhoneUser phoneuser;

	public String execute()
    {
		phoneuser = us.query(mail, IRemoteConstantDefine.USER_STATUS_ENABLED, platform);
		if ( phoneuser == null )
		{
			resultCode = ErrorCodeDefine.USERNAME_NOT_EXSIT;
			return Action.ERROR;
		}
		resultCode = RandCodeHelper.checkRandcode(mail , IRemoteConstantDefine.RANDCODE_TYPE_MAILUSER_RESETPASSWORD, randcode , platform);
		if ( resultCode != ErrorCodeDefine.SUCCESS )
			return Action.ERROR;
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setRandcode(String randcode) {
		this.randcode = randcode;
	}

	public int getPlatform() {
		return platform;
	}

	public String getMail() {
		return mail;
	}

	public String getRandcode() {
		return randcode;
	}
}
