package com.iremote.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.iremote.action.helper.RequestHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ThirdPartToken;
import com.iremote.service.ThirdPartService;
import com.iremote.service.ThirdPartTokenService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ThirdpartTokenCheckInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(ThirdpartTokenCheckInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception 
	{
		HttpServletRequest request = ServletActionContext.getRequest();  
		String token = request.getParameter("token");
		
		if ( StringUtils.isBlank(token) )
		{
			sessiontimeoutlog(request , "no token");
			return "TOKEN_ERROR" ;
		}
		
		ThirdPartTokenService tsvr = new ThirdPartTokenService();
		ThirdPartToken tpt = tsvr.querybytoken(token);
		
		if ( tpt == null || tpt.getValidtime().before(new Date()))
		{
			sessiontimeoutlog(request , "token error");
			return "TOKEN_ERROR" ;
		}
		
		ThirdPartService tps = new ThirdPartService();
		ThirdPart tp = tps.query(tpt.getCode());
		ActionContext.getContext().getSession().put(IRemoteConstantDefine.SESSION_THIRDPART , tp);
		
	   if ( PropertyUtils.isWriteable(invocation.getAction(), "thirdpart")) 
	    	 PropertyUtils.setProperty(invocation.getAction(), "thirdpart", tp);
		
	   log.info(tp.getName());
	   return invocation.invoke();
	}

	private void sessiontimeoutlog(HttpServletRequest request , String err)
	{
		if ( log.isInfoEnabled())
			log.info(String.format("Request from %s arrive:%s , %s " ,  RequestHelper.getRemoteIp() , request.getRequestURI() , err) );
	}
}
