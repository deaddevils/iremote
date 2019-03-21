package com.iremote.user;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.User;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class UpdateUserInfo {
	private int resultCode = 0 ;
	private String email;
	private String phone;
	private String question;
	private String answer;

	public String execute()
	{
		User user = (User) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		if ( user == null )
		{
			resultCode = ErrorCodeDefine.SESSION_TIMEOUT;
			return Action.SUCCESS;
		}
		
		UserService svr = new UserService();
		User u = svr.getUser(user.getUsername());
		
		if ( u == null )
		{
			resultCode = ErrorCodeDefine.UNKNOW_ERROR;
			return Action.SUCCESS;
		}
		
		u.setEmail(email);
		u.setPhone(phone);
		u.setQuestion(question);
		u.setAnswer(answer);

		return Action.SUCCESS;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getResultCode() {
		return resultCode;
	}
	
	
}
