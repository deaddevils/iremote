package com.iremote.user;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.User;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;

public class QueryUserQuestion {

	private int resultCode = 0 ;
	private String username;
	private String question;
	
	public String execute()
	{
		UserService svr = new UserService();
		User user = svr.getUser(username);
		if ( user == null )
		{
			resultCode = ErrorCodeDefine.USERNAME_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		this.question = user.getQuestion();
		
		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public String getQuestion() {
		return question;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
