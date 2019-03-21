package com.iremote.test.db;

import org.hibernate.Query;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.HibernateUtil;

public class Db {

	public static void init()
	{
		HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
		HibernateUtil.beginTransaction();
	}
	
	public static void commit()
	{
		HibernateUtil.commit();
		HibernateUtil.closeSession();
	}
	
	public static void rollback()
	{
		HibernateUtil.rollback();
		HibernateUtil.closeSession();
	}
	
	public static void update(String[] param , String[] filter)
	{
		Query q = HibernateUtil.getSession().createQuery(createUpdateHql(param , filter));
		q.executeUpdate();
	}
	
	public static void delete(String[] param)
	{
		Query q = HibernateUtil.getSession().createQuery(createDeleteHql(param));
		q.executeUpdate();
	}
	
	public static int count(String[] param)
	{
		Query q = HibernateUtil.getSession().createQuery(createCountHql(param));
		return ((Long)q.uniqueResult()).intValue();
	}
	
	public static void sleep(int  millis)
	{
		try {
			Thread.sleep( millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static String createUpdateHql(String[] param , String[] filter)
	{
		StringBuffer sb = new StringBuffer(" Update ");
		sb.append(filter[0]);
		sb.append(" set ");
		sb.append(createupdate(param));
		
		sb.append(" where ");
		sb.append(createFilter(filter));
		
		return sb.toString();
	}
	
	private static String createDeleteHql(String[] param)
	{
		StringBuffer sb = new StringBuffer(" Delete ");
		sb.append(param[0]);
		
		String sf = createFilter(param);
		
		if ( sf != null && sf.length() > 0 )
			sb.append(" where " ).append(sf);
		return sb.toString();
	}
	
	private static String createCountHql(String[] param)
	{
		StringBuffer sb = new StringBuffer(" Select count(*) from ");
		sb.append(param[0]);
		
		String sf = createFilter(param);
		
		if ( sf != null && sf.length() > 0 )
			sb.append(" where " ).append(sf);
		return sb.toString();
	}
	
	private static String createFilter(String[] param)
	{
		StringBuffer sb = new StringBuffer();
		for ( int i = 1 ; i < param.length - 1 ; i += 2 )
			sb.append(param[i]).append(" = '").append(param[i+1]).append("' and ");
		return sb.substring(0, sb.length() - "and ".length());
	}
	
	private static String createupdate(String[] param)
	{
		StringBuffer sb = new StringBuffer();
		for ( int i = 0 ; i < param.length - 1 ; i += 2 )
			sb.append(param[i]).append(" = '").append(param[i+1]).append("' , ");
		return sb.substring(0, sb.length() - ", ".length());
	}
}
