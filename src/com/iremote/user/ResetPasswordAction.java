package com.iremote.user;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.User;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;

public class ResetPasswordAction {

	private int resultCode = 0 ;
	private String username;
	private String answer;
	private String password;

	public String execute()
	{
		UserService svr = new UserService();
		User user = svr.getUser(username);
		if ( user == null || answer == null || !answer.equals(user.getAnswer()))
		{
			resultCode = ErrorCodeDefine.USERNAME_OR_ANSWER_WRONG;
			return Action.SUCCESS;
		}
		
		String ep = svr.encryptPassword(user.getUsername() , password);
		
		user.setPassword(ep);
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
