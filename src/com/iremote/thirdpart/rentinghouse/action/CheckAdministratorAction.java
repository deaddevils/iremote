package com.iremote.thirdpart.rentinghouse.action;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.CommunityAdministrator;
import com.iremote.service.CommunityAdministratorService;
import com.opensymphony.xwork2.Action;

public class CheckAdministratorAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String loginname;
	private int exists;
	
	public String execute()
	{
		
		CommunityAdministratorService svr = new CommunityAdministratorService();
		CommunityAdministrator ca = svr.querybyloginname(loginname);
		
		if ( ca == null )
		{
			exists = 0 ;
			return Action.SUCCESS;
		}
		else 
			exists = 1 ;
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getExists() {
		return exists;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	
	
}
