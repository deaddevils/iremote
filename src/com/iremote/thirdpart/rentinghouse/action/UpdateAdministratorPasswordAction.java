package com.iremote.thirdpart.rentinghouse.action;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.CommunityAdministrator;
import com.iremote.domain.ThirdPart;
import com.iremote.service.CommunityAdministratorService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class UpdateAdministratorPasswordAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String loginname;
	private String password;
	
	public String execute()
	{
		
		CommunityAdministratorService svr = new CommunityAdministratorService();
		CommunityAdministrator ca = svr.querybyloginname(loginname);
		
		if ( ca == null )
		{
			resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		ThirdPart tp = (ThirdPart)ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_THIRDPART);
		if ( ca.getThirdpartid() != tp.getThirdpartid() )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		UserService us = new UserService();
		String ep = us.encryptPassword(ca.getLogicname() , password);
		
		PhoneUserService ps = new PhoneUserService();
		ps.updatePassword(IRemoteConstantDefine.DEFAULT_COUNTRYCODE  , loginname, tp.getPlatform(), ep);
		
		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
