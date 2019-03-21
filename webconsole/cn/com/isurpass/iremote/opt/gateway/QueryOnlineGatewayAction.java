package cn.com.isurpass.iremote.opt.gateway;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.infraredtrans.ConnectionManager;
import com.opensymphony.xwork2.Action;

public class QueryOnlineGatewayAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int onlinenumber ;
	
	public String execute()
	{
		this.onlinenumber = ConnectionManager.getNumberofOnlineGateway();
		return Action.SUCCESS;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public int getOnlinenumber()
	{
		return onlinenumber;
	}
	
	
}
