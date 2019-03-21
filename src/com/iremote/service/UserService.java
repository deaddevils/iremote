package com.iremote.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.digest.PooledStringDigester;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.User;

public class UserService {
	
	private static Log log = LogFactory.getLog(UserService.class);
	private static PooledStringDigester digester = new PooledStringDigester();
	
	static 
	{
		digester.setPoolSize(8);
		digester.setIterations(1059);
	}

	public User getUser(String username)
	{
		CriteriaWrap cw = new CriteriaWrap(User.class.getName());
		
		cw.add(ExpWrap.eq("username", username));
		
		return (User)cw.uniqueResult();
	}
	
	public void save(User user)
	{
		user.setPassword(user.getPassword());
		HibernateUtil.getSession().save(user);
	}
	
	public void updatePassword(String loginname , String password)
	{
		User user = getUser(loginname);
		if ( user == null )
			return ;
		user.setPassword(password);
	}
	
	public String encryptPassword(String username , String password)
	{		
		String pw = username + password ;
		return digester.digest(pw);
	}
	
	public boolean checkUserPassword(String loginname , String password)
	{
		User u = getUser(loginname);
		if ( u == null )
			return false ;
		return checkPassword(loginname , password, u.getPassword());
	}
	
	public boolean checkPassword(String loginname , String password , String enpassword)
	{
		try
		{
			return digester.matches(loginname + password, enpassword);
		}
		catch(Throwable t)
		{
			log.warn(t.getMessage());
			return false ;
		}
	}
	
	public static void main(String arg[])
	{
		String name = "admin";
		String pw = "admin";
		
		long s = System.currentTimeMillis();
		UserService svr = new UserService();
		String str = svr.encryptPassword(name , pw);
		System.out.println(System.currentTimeMillis() - s);
		System.out.println(str);
		
		s = System.currentTimeMillis();
		boolean b = digester.matches(name + pw.substring(0,5) +"" , str);
		System.out.println(System.currentTimeMillis() - s);
		System.out.println(b);
	}
}
