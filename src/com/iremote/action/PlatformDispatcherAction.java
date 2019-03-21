package com.iremote.action;


public class PlatformDispatcherAction {

	public String execute()
	{
//		PhoneUser u = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
//		if ( u.getPlatform() == IRemoteConstantDefine.PLATFORM_ASININFO )
//			return "asiainfo";
//		else 
			return "normal";
	}
}
