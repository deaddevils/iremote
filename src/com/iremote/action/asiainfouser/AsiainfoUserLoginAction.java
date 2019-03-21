package com.iremote.action.asiainfouser;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.phoneuser.CookieHelper;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class AsiainfoUserLoginAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String usertoken ;
	private String alias;
	private int smsnumber;
	private int callnumber;
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber ;
	
	public String execute()
	{
		if ( AsiainfoHttpHelper.asiainfoUserAuth(usertoken) == false )
		{
			resultCode = ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG;
			return Action.SUCCESS;
		}
		
		JSONObject json = AsiainfoHttpHelper.asiainfoUserinfo(usertoken);
		
		if ( !json.containsKey("resultCode") || json.getIntValue("resultCode") != 0 )
			return Action.SUCCESS;

		json = json.getJSONObject("message");
		if ( !json.containsKey("phone"))
			return Action.SUCCESS;
		phonenumber = json.getString("phone");
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser user = pus.query(countrycode , phonenumber , PhoneUserHelper.createplatform(IRemoteConstantDefine.PLATFORM_ASININFO));
		
		if ( user == null )
		{
			user = PhoneUserHelper.savePhoneUser(phonenumber , "" );
			NotificationHelper.saveDefaultNotificationSetting(user); 
			user.setPlatform(IRemoteConstantDefine.PLATFORM_ASININFO);
			//user.setUsername(json.getString(""));
		}

		alias = user.getAlias();
		smsnumber = ServerRuntime.getInstance().getDefaultsmscount() -  user.getSmscount();
		if ( smsnumber < 0 ) smsnumber = 0 ;
		callnumber = ServerRuntime.getInstance().getDefaultcallcount() - user.getCallcount();
		if ( callnumber < 0 ) callnumber = 0 ;
		
		user.setToken(usertoken);
		user.setOpenId(json.getString("openId"));
		user.setPlatform(IRemoteConstantDefine.PLATFORM_ASININFO);
		ActionContext.getContext().getSession().put(IRemoteConstantDefine.SESSION_USER, user);
		CookieHelper.setUsertoken(user);
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getAlias() {
		return alias;
	}

	public int getSmsnumber() {
		return smsnumber;
	}

	public int getCallnumber() {
		return callnumber;
	}

	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}

	public String getPhonenumber() {
		return phonenumber;
	}
	
	
}
