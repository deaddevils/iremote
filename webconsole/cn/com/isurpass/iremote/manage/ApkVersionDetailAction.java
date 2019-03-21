package cn.com.isurpass.iremote.manage;

import java.util.List;

import com.iremote.domain.AppVersion;
import com.iremote.service.AppVersionService;
import com.opensymphony.xwork2.Action;

public class ApkVersionDetailAction 
{
	private List<AppVersion> appversions;
	
	public String execute()
	{
		AppVersionService avs = new AppVersionService();
		appversions = avs.query();
		return Action.SUCCESS;
	}

	public List<AppVersion> getAppversions() {
		return appversions;
	}
	
	
}
