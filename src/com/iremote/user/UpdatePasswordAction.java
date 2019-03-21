package com.iremote.user;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.User;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class UpdatePasswordAction {
	private int resultCode = 0 ;
	private String password;
	private String newpassword;
	
	public String execute()
	{
		User user = (User) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		if ( user == null )
		{
			resultCode = ErrorCodeDefine.SESSION_TIMEOUT;
			return Action.SUCCESS;
		}
		
		UserService svr = new UserService();
		if ( password == null 
				|| !svr.checkPassword(user.getUsername() , password, user.getPassword()))
		{
			resultCode = ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG;
			return Action.SUCCESS;
		}

		String ep = svr.encryptPassword(user.getUsername() , newpassword);
		svr.updatePassword(user.getUsername(), ep);

		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
}
