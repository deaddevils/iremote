package com.iremote.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class HttpsInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(HttpsInterceptor.class);
	
	private static Set<String> HTTPSURL = new HashSet<String>();
	
	static
	{
		HTTPSURL.add("/iremote/phoneuser/register");
		HTTPSURL.add("/iremote/phoneuser/login");
		HTTPSURL.add("/iremote/phoneuser/updatepassword");
		HTTPSURL.add("/iremote/phoneuser/resetpassword");
		
		HTTPSURL.add("/iremote/user/register");
		HTTPSURL.add("/iremote/user/login");
		HTTPSURL.add("/iremote/user/updatepassword");
		HTTPSURL.add("/iremote/user/resetpassword");
		
		HTTPSURL.add("/iremote/thirdpart/login");
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception 
	{
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		
		String uri = request.getRequestURI();

		if ( !"https".equalsIgnoreCase(request.getScheme()) )
		{
			log.warn(String.format("%s should use https", uri));
			//return "HTTPS_REQUIRED" ;
		}
		if ( log.isInfoEnabled() && StringUtils.isNotBlank(request.getQueryString()) )
			log.info(request.getQueryString());
		
		return invocation.invoke();
	}

}
