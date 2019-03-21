package com.iremote.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.iremote.action.helper.RequestHelper;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.AnnotationUtils;

public abstract class DataPrivilegeBaseInterceptor<T> extends AbstractInterceptor
{
	private static final long serialVersionUID = 1L;
	
	private static final String NO_PRIVILEGE = "NO_PRIVILEGE";
	private static Log log = LogFactory.getLog(DataPrivilegeBaseInterceptor.class);

	protected abstract T queryUser();
	protected abstract IURLDataPrivilegeChecker<T> queryChecker(DataPrivilegeType privlegetype , String domain);
	protected abstract boolean needcheck(DataPrivilege dp);

	@Override
	public String intercept(ActionInvocation aic) throws Exception
	{
		Object action = aic.getAction();
		
		DataPrivilege dp = AnnotationUtils.findAnnotation(action.getClass(), DataPrivilege.class);
		DataPrivileges dps = AnnotationUtils.findAnnotation(action.getClass(), DataPrivileges.class);
		
		if ( dp == null && dps == null )
			return aic.invoke();
		
		T user = queryUser();
		if ( user == null )
		{
			noprivilegelog();
			return NO_PRIVILEGE;
		}
	
		if ( check(dp , user) == false )
		{
			noprivilegelog();
			return NO_PRIVILEGE;
		}
		
		if ( dps != null )
		{
			for ( DataPrivilege dpv : dps.dataprivilege())
				if (check(dpv , user) == false )
				{
					noprivilegelog();
					return NO_PRIVILEGE;
				}
		}
		
		return aic.invoke();
	}
	
	
	private void noprivilegelog()
	{
		if ( log.isInfoEnabled())
		{
			HttpServletRequest request = ServletActionContext.getRequest();  
			log.info(String.format("Request from %s arrive:%s , no privilege " ,  RequestHelper.getRemoteIp() , request.getRequestURI()) );
		}
	}

	private boolean check(DataPrivilege dp , T user)
	{
		if ( dp == null )
			return true ;
		
		if ( needcheck(dp) == false )
			return true;
		
		IURLDataPrivilegeChecker<T> dpc = queryChecker(dp.dataprivilgetype(), dp.domain());
		if ( dpc == null )
			return false;
		
		dpc.setUser(user);
		
		HttpServletRequest request = ServletActionContext.getRequest();  

		String[] params = dp.parameters();
		if ( params != null )
		{
			Map<String , String> map = new HashMap<String , String>();
			
			for ( String p : params )
				map.put(p, request.getParameter(p));
			dpc.SetParameters(map);
		}
		
		String param = dp.parameter();
		dpc.setParameter(request.getParameter(param));
		
		if ( dpc.checkprivilege() == false )
			return false;
		
		return true;
	}
	
}
