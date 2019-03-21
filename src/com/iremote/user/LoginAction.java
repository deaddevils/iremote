package com.iremote.user;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.User;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction  {
	
	private static Log log = LogFactory.getLog(LoginAction.class);
	private int resultCode = 0 ;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String question;
	private String answer;
	
	public String execute()
	{
		UserService svr = new UserService();
		User user = svr.getUser(username);
		if ( user == null || password == null 
				|| !svr.checkPassword(user.getUsername() , password, user.getPassword()))
		{
			resultCode = ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG;
			return Action.SUCCESS;
		}

		try {
			PropertyUtils.copyProperties(this , user );
		} catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			this.resultCode = ErrorCodeDefine.UNKNOW_ERROR;
			return Action.SUCCESS;
			
		} 
		
		ActionContext.getContext().getSession().put(IRemoteConstantDefine.SESSION_USER, user);
		return Action.SUCCESS;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public int getResultCode() {
		return resultCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
