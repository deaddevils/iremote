package com.iremote.action.phoneuser;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.sms.RandCodeHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserService;
import com.iremote.service.UserTokenService;
import com.opensymphony.xwork2.Action;

public class PhoneUserResetpassword {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber;
	private int platform;
	private String randcode;
	private String password;
	
	public String execute()
	{
		UserService svr = new UserService();
		PhoneUserService ps = new PhoneUserService();
		
		resultCode = RandCodeHelper.checkRandcode(countrycode , phonenumber, 2, randcode , platform);
		if ( resultCode != ErrorCodeDefine.SUCCESS )
			return Action.SUCCESS;
		
		PhoneUser user = ps.query(countrycode,phonenumber , platform);
		
		if ( user == null )
		{
			resultCode = ErrorCodeDefine.USER_HAS_NOT_REGISTED;
			return Action.SUCCESS;
		}
		
		String ep = svr.encryptPassword(user.getPhonenumber() , password);
		
		user.setPassword(ep);
		
		UserTokenService uts = new UserTokenService();
		uts.deletebyphonuserid(user.getPhoneuserid() , CookieHelper.getUsertoken());

		PhoneUserHelper.sendPasswordChangedMessage(user);
		return Action.SUCCESS;
	}
	
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public void setRandcode(String randcode) {
		this.randcode = randcode;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getResultCode() {
		return resultCode;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
}
