package com.iremote.action.mailuser;

import com.iremote.action.sms.RandCodeHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Randcode;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RandcodeService;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class MailUserResetPasswordSetAction {
	private String mail;
	private int platform;
	private String randcode;
	private String newpassword;
	private String comfirmpassword;
	private int resultCode = ErrorCodeDefine.SUCCESS ;

	private PhoneUserService us = new PhoneUserService();
	private RandcodeService rs = new RandcodeService();
	private UserService svr = new UserService();

	private PhoneUser phoneuser ;
	
	public String execute()
	{
		if ( StringUtils.isNotBlank(this.mail ))
			this.mail = this.mail.trim();
		if(!JWStringUtils.checkEmail(mail)){
			resultCode = ErrorCodeDefine.MAIL_FORMAT_ERROR;
			return Action.ERROR;
		}
		if(newpassword == null || newpassword.length() == 0){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.ERROR;
		}
		if(comfirmpassword == null || comfirmpassword.length() == 0){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.ERROR;
		}
		if(!comfirmpassword.equals(newpassword)){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.ERROR;
		}
		phoneuser = us.query(mail, IRemoteConstantDefine.USER_STATUS_ENABLED, platform);
		if ( phoneuser == null )
		{
			resultCode = ErrorCodeDefine.USERNAME_NOT_EXSIT;
			return Action.ERROR;
		}
		resultCode = RandCodeHelper.checkRandcode(mail , IRemoteConstantDefine.RANDCODE_TYPE_MAILUSER_RESETPASSWORD, randcode , platform);
		if ( resultCode != ErrorCodeDefine.SUCCESS )
			return Action.ERROR;

		Randcode rc = rs.querybycode(randcode);
		phoneuser.setLastupdatetime(new Date());
		String ep = svr.encryptPassword(mail, newpassword);
		phoneuser.setPassword(ep);
		us.save(phoneuser);
		rs.delete(rc);
		return Action.SUCCESS;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public void setRandcode(String randcode) {
		this.randcode = randcode;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public void setComfirmpassword(String comfirmpassword) {
		this.comfirmpassword = comfirmpassword;
	}

	public int getResultCode() {
		return resultCode;
	}
}
