package com.iremote.action.phoneuser;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserService;
import com.iremote.service.UserTokenService;
import com.opensymphony.xwork2.Action;

public class PhoneUserUpdatepasswordAction {

	private int resultCode =  ErrorCodeDefine.SUCCESS ;
	private String password;
	private String newpassword;
	private PhoneUser phoneuser ;

	public String execute()
	{
		if ( phoneuser == null )
		{
			resultCode = ErrorCodeDefine.SESSION_TIMEOUT;
			return Action.SUCCESS;
		}

		UserService svr = new UserService();
		if ( password == null
				|| !svr.checkPassword(phoneuser.getPhonenumber() , password, phoneuser.getPassword()))
		{
			resultCode = ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG;
			return Action.SUCCESS;
		}

		String ep = svr.encryptPassword(phoneuser.getPhonenumber() , newpassword);
		
		PhoneUserService ps = new PhoneUserService();
		ps.updatePassword(phoneuser.getCountrycode() , phoneuser.getPhonenumber(), phoneuser.getPlatform(), ep);
		
		UserTokenService uts = new UserTokenService();
		uts.deletebyphonuserid(phoneuser.getPhoneuserid() , CookieHelper.getUsertoken());
		
		PhoneUserHelper.sendPasswordChangedMessage(phoneuser);
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
}
