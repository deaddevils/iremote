package com.iremote.action.phoneuser;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.UserToken;
import com.iremote.interceptor.SessionInterceptor;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserTokenService;
import com.opensymphony.xwork2.Action;

public class LogoutAction {

	public String execute(){
		HttpServletRequest request = ServletActionContext.getRequest();  
	    String token = SessionInterceptor.getCookie(request, "Usertoken");
	    
	    if ( token == null || token.length() == 0 ){
	    	token = request.getParameter("Usertoken");
	    }
	    if ( token == null || token.length() == 0 ){
	    	return Action.SUCCESS;
	    }
	    UserTokenService svr = new UserTokenService();
	    PhoneUserService us = new PhoneUserService();
	    UserToken usertoken = svr.querybytoken(token);
	    
	    int phoneuserid = usertoken.getPhoneuserid();
	    int platform = us.query(phoneuserid).getPlatform();
	    svr.delete(usertoken);
	    if(IRemoteConstantDefine.PLATFORM_AMETA==platform){
	    	return "ameta";
	    }
	    return Action.SUCCESS;
	}
	
}
