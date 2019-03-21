package com.iremote.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.iremote.action.helper.RequestHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserToken;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserService;
import com.iremote.service.UserTokenService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(SessionInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception 
	{
		 HttpServletRequest request = ServletActionContext.getRequest();  
	     String token = getCookie(request, "Usertoken");
	     //log.info(String.format("Usertoken %s:%s" , request.getRequestURI() , token));
	     //log.info(token);
	     if ( token == null || token.length() == 0 )
	    	 token = request.getParameter("Usertoken");
	     if ( token == null || token.length() == 0 )
	     {
	    	 sessiontimeoutlog(request , "no token");
	    	 return "SESSION_TIMEOUT";
	     }
	     UserTokenService svr = new UserTokenService();
	     UserToken ut = svr.querybytoken(token);
	     if ( ut == null )
	     {
	    	 sessiontimeoutlog(request , "session timeout");
	    	 return "SESSION_TIMEOUT";
	     }
	     
	     PhoneUserService pus = new PhoneUserService();
	     PhoneUser pu = pus.query(ut.getPhoneuserid());
	     if ( pu == null )
	     {
	    	 sessiontimeoutlog(request , "no user");
	    	 return "SESSION_TIMEOUT";
	     }
	     
	     String securitytoken = getCookie(request, "securitytoken");
	     if ( StringUtils.isNotBlank(ut.getSecuritytoken()))
	     {
	    	 if ( StringUtils.isBlank(securitytoken))
	    	 {
	    		 sessiontimeoutlog(request ,"Security token is null");
	    		 return "SESSION_TIMEOUT";
	    	 }
	    	 else 
	    	 {
	    		 UserService us = new UserService();
	    		 if ( us.checkPassword(pu.getPhonenumber(), securitytoken, ut.getSecuritytoken()) == false)
	    		 {
	    			 sessiontimeoutlog(request ,"Security token error");
	    			 return "SESSION_TIMEOUT";
	    		 }
	    	 }
	     }
	     
	     ActionContext.getContext().getSession().put(IRemoteConstantDefine.SESSION_USER , pu);
	     ActionContext.getContext().getSession().put(IRemoteConstantDefine.SESSION_TOKEN_ID , new Integer(ut.getTokenid()));

	     if ( PropertyUtils.isWriteable(invocation.getAction(), "phoneuser")) 
	    	 PropertyUtils.setProperty(invocation.getAction(), "phoneuser", 
	    			 ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER));

	     String rst = invocation.invoke();
		
	     HibernateUtil.getSession().evict(ut);
	     
	     svr.updateLastUpdatetime(ut.getTokenid());

	     return rst ;
	}
	
	private void sessiontimeoutlog(HttpServletRequest request , String err)
	{
		if ( log.isInfoEnabled())
			log.info(String.format("Request from %s arrive:%s , %s " ,  RequestHelper.getRemoteIp() , request.getRequestURI() , err) );
	}
	
	public static String getCookie(String key)
	{
		HttpServletRequest request = ServletActionContext.getRequest();  
		return getCookie(request , key);
	}
	
	public static String getCookie(HttpServletRequest request, String key) {  
        Cookie[] cookies = request.getCookies();  
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                if (key.equals(cookie.getName())) {  
                	return cookie.getValue();  
                }  
            }  
        }  
        return null;  
    }  
}
