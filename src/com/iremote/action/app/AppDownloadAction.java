package com.iremote.action.app;

import com.iremote.domain.OemProductor;
import com.iremote.service.OemProductorService;
import com.opensymphony.xwork2.Action;

public class AppDownloadAction
{
	private int platform = 0 ;
	
	private String androidappdownloadurl;
	private String iosappdownloadurl ;
	
	public String execute()
	{
		OemProductorService svr = new OemProductorService();
		OemProductor op = svr.querybyplatform(platform);
		
		if ( op == null )
			return Action.ERROR;
		
		this.androidappdownloadurl = op.getAndroidappdownloadurl();
		this.iosappdownloadurl = op.getIosappdownloadurl();
		
		return Action.SUCCESS;
	}

	public String getAndroidappdownloadurl()
	{
		return androidappdownloadurl;
	}

	public String getIosappdownloadurl()
	{
		return iosappdownloadurl;
	}

	public void setPlatform(int platform)
	{
		this.platform = platform;
	}
	
	
}
