package com.iremote.action.phoneuser;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.iremote.common.Utils;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserToken;
import com.iremote.interceptor.SessionInterceptor;
import com.iremote.service.UserTokenService;

public class CookieHelper {

	public static void setUsertoken(PhoneUser user)
	{
		String oldtoken = SessionInterceptor.getCookie("Usertoken");
		
		String token = Utils.createtoken();
		Cookie c = new Cookie("Usertoken" , token);
		c.setMaxAge(10*365*24*3600);
		c.setPath("/iremote");
		//c.setHttpOnly(true);
		//c.setSecure(true);
		ServletActionContext.getResponse().addCookie(c);
		
		UserToken ut = new UserToken();
		ut.setPhoneuserid(user.getPhoneuserid());
		ut.setToken(token);
		
		UserTokenService uts = new UserTokenService();
		uts.save(ut);
		
		if ( oldtoken != null && oldtoken.length() > 0 )
		{
			UserToken ou = uts.querybytoken(oldtoken);
			if ( ou != null )
				uts.delete(ou);
		}
	}
	
	public static String getUsertoken()
	{
		return SessionInterceptor.getCookie("Usertoken");
	}
}
