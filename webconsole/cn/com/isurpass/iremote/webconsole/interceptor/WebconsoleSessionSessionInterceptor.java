package cn.com.isurpass.iremote.webconsole.interceptor;

import com.iremote.interceptor.SessionInterceptor;
import com.opensymphony.xwork2.ActionInvocation;

public class WebconsoleSessionSessionInterceptor extends SessionInterceptor
{
	private static final long serialVersionUID = -885738480517000550L;

	@Override
	public String intercept(ActionInvocation arg0) throws Exception
	{
		String rst = super.intercept(arg0);
		
		if ( "SESSION_TIMEOUT".equals(rst))
			return "nologin";
		return rst;
	}
	
}
