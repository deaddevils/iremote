package com.iremote.user;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.User;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;


public class RegisterUserAction {
	
	private int resultCode = 0 ;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String question;
	private String answer;
	
	public String execute()
	{
		User user =	new User();
		user.setAnswer(answer);
		user.setEmail(email);
		user.setPassword(password);
		user.setPhone(phone);
		user.setQuestion(question);
		user.setUsername(username);
		
		UserService svr = new UserService();
		if ( svr.getUser(user.getUsername()) != null )
		{
			this.resultCode = ErrorCodeDefine.USERNAME_CRASH;
			return Action.SUCCESS;
		}

		String ep = svr.encryptPassword(user.getUsername() , user.getPassword());
		user.setPassword(ep);
		svr.save(user);

		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public static void main(String arg[])
	{
		//Transaction tx = HibernateUtil.getSession().beginTransaction();
		
		RegisterUserAction ra = new RegisterUserAction();
		ra.setPassword("123456");
		ra.setUsername("test1");
		
		ra.execute();
		
		//tx.commit();
		
		//HibernateUtil.closeSession();
	}


}
