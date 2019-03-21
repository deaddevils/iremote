package com.iremote.interceptor;


import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.jms.JMSUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class HibernateReleaseInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	
	private static Set<String> NONE_TRANS_URL = new HashSet<String>();
	
	static
	{
		//NONE_TRANS_URL.add("/iremote/device/lock/getlockoperationstatus");
	}
	
	//private static Log log = LogFactory.getLog(HibernateReleaseInterceptor.class);
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception 
	{

		ActionContext actionContext = actionInvocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		
		String uri = request.getRequestURI();
		if ( NONE_TRANS_URL.contains(uri))
			return actionInvocation.invoke();
		
		String rst = null ;
		
		HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_GUI);
		HibernateUtil.beginTransaction();
		try
		{
			rst = actionInvocation.invoke();
			HibernateUtil.commit();
		}
		catch(Exception e)
		{
			HibernateUtil.rollback();
			throw e ;
		}
		finally
		{
			HibernateUtil.closeSession();
			JMSUtil.commitmessage();
		}
		return rst ;
	}

}
