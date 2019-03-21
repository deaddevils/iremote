package com.iremote.service;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.UserToken;
import com.iremote.test.db.Db;

public class UserTokenServiceTest {

	public static void main(String arg[])
	{
		Db.init();
		UserTokenService svr = new UserTokenService();
		//svr.updateLastUpdatetime("ad4cf85651ae44648512f5a4a8ab578b943707");
		
		Db.commit();
	}
	
//	public static void main(String arg[])
//	{
//		HibernateUtil.beginTransaction();
//		UserTokenService svr = new UserTokenService();
//		svr.querybytoken("fdsfdsafdsa");
//		HibernateUtil.commit();
//		HibernateUtil.closeSession();
//		
//		HibernateUtil.beginTransaction();
//		UserToken ut = svr.querybytoken("539f506b995f40979598faa978f7b8cc990179");
//	     if ( ut == null )
//			System.out.println("null");
//		else 
//			System.out.println(ut.getPhoneuserid());
//	     
//	     HibernateUtil.commit();
//			HibernateUtil.closeSession();
//	}
}
