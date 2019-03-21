package com.iremote.action.phoneuser;

import java.util.Date;

import org.hibernate.Transaction;

import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserService;
import com.iremote.test.db.Db;
@SuppressWarnings("unused")
public class RegistePhoneUserActionTest {

	public static void main(String arg[])
	{
		UserService svr = new UserService();
		System.out.println(svr.encryptPassword("6268220996", "123456"));
	}
	
	public static void main1(String arg[])
	{
		Db.init();
		RegistePhoneUserAction a = new RegistePhoneUserAction();
		
		String phonenumber = "13543114466";
		String password = "123456";
		
		PhoneUser ph = new PhoneUser();
		ph.setPhonenumber(phonenumber);
	
		UserService svr = new UserService();
		String ep = svr.encryptPassword(phonenumber, password);
		
		ph.setCountrycode("86");
		ph.setPassword(ep);
		ph.setCreatetime(new Date());
		ph.setLastupdatetime(new Date());

		PhoneUserService us = new PhoneUserService();
		int id = us.save(ph);
		ph.setAlias(Utils.createAlias(id));
		
		Db.commit();
	}
}
