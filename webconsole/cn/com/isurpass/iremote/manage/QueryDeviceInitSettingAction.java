package cn.com.isurpass.iremote.manage;

import com.iremote.domain.SystemParameter;
import com.iremote.service.SystemParameterService;
import com.opensymphony.xwork2.Action;

public class QueryDeviceInitSettingAction 
{
	private String version ;
	
	public String execute()
	{
		SystemParameterService sps = new SystemParameterService();
		SystemParameter sp = sps.querybykey("DeviceInitSetting_Version");
		if ( sp == null )
			return Action.SUCCESS;
		version = sp.getStrvalue();
		return Action.SUCCESS;
	}

	public String getVersion() {
		return version;
	}
	
	
}
