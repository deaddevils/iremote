package com.iremote.interceptor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.RequestHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.IUser;
import com.iremote.domain.OperationLog;
import com.iremote.domain.ThirdPart;
import com.iremote.service.OperationLogService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

public class LogInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(LogInterceptor.class);
		
	private static Set<String> NOLOG_URL = new HashSet<String>();
	private static Set<String> NOLOG_PARAMETER = new HashSet<String>();
	static
	{
		NOLOG_URL.add("/iremote/device/lock/getlockoperationstatus");
		NOLOG_URL.add("/iremote/thirdpart/event/queryevent");
		
		NOLOG_PARAMETER.add("password");
		NOLOG_PARAMETER.add("newpassword");
		NOLOG_PARAMETER.add("token");
		NOLOG_PARAMETER.add("securitycode");
		NOLOG_PARAMETER.add("superpw");
		NOLOG_PARAMETER.add("Usertoken");
		NOLOG_PARAMETER.add("securitytoken");
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception 
	{
		if ( !log.isInfoEnabled() )
			return invocation.invoke();

		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		
		String uri = request.getRequestURI();
		if ( NOLOG_URL.contains(uri))
			return invocation.invoke();
		
		OperationLog opl = new OperationLog();
		logRequest(opl , request);
		
		String rst = invocation.invoke();
		
		Object action = invocation.getAction();
		
		logResponse(opl , action);
		saveOperaionlog(opl);
		
		return rst ;
	}
	
	protected void saveOperaionlog(OperationLog opl)
	{
		OperationLogService svr = new OperationLogService();
		svr.save(opl);
	}
	
	private void logResponse(OperationLog opl , Object action)
	{
		
		try
		{
			JSONObject obj = (JSONObject)JSON.toJSON(action);
			if ( obj.containsKey("token"))
				obj.put("token", "******");
			String str = obj.toJSONString();
			
			log.info(str);
			if ( str != null &&  str.length() > 1024 )
				str = str.substring(0,1024);
			opl.setResult(str);
			opl.setResultCode(obj.getInteger("resultCode"));
		}catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
	}

	private String getUsername()
	{
		try
		{
			Object obj = ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
			if ( obj != null )
			{
				IUser user = (IUser)obj;
				return user.getUsername();
			}
			obj = ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_THIRDPART);
			if ( obj != null )
			{
				ThirdPart tp = ( ThirdPart)obj;
				return tp.getName();
			}
		}
		catch(Throwable t)
		{
			log.error(t.getMessage());
		}
		return "";
	}
	
	private void logRequest(OperationLog opl , HttpServletRequest request)
	{
		String username = getUsername();
		log.info(String.format("Request from %s(%s) arrive:%s" , username, RequestHelper.getRemoteIp() , request.getRequestURI()) );
		opl.setUsername(username);
		opl.setUserip(RequestHelper.getRemoteIp());
		opl.setRequesturl(request.getRequestURI());
		
		Map<String , String[]> map = request.getParameterMap();

		for(String v : map.keySet())
		{
			try
			{
				StringBuffer sb = new StringBuffer();
				sb.append(v).append(":");
				if ( NOLOG_PARAMETER.contains(v))
				{
					sb.append("*********");
					log.info(sb.toString());
				}
				else 
				{
					String[] sa = map.get(v);
					for ( String str : sa )
						sb.append(" ").append(str);
					log.info(sb.toString());
				}
				
			}
			catch(Exception e)
			{
				log.error("", e);
			}
		}
		JSONObject obj = (JSONObject)JSON.toJSON(map);
		for ( String key : NOLOG_PARAMETER)
		{
			if ( obj.containsKey(key))
				obj.put(key, "*******");
		}
		String str = obj.toJSONString();
		if ( str.length() > 1024 )
			str = str.substring(0 , 1024);
		opl.setRequestdata(str);
		opl.setDeviceid(obj.getString("deviceid"));
		opl.setZwavedeviceid(obj.getString("zwavedeviceid"));
	}
}
