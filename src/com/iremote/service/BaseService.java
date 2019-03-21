package com.iremote.service;

import com.iremote.common.Hibernate.HibernateUtil;

public class BaseService<T>
{
	public int save(T t)
	{
		Object id = HibernateUtil.getSession().save(t);
		if ( id instanceof Long )
			return ((Long) id).intValue();
		else if ( id instanceof Integer )
			return ((Integer) id).intValue();
		return 0 ;
	}
	
	public void delete(T t)
	{
		HibernateUtil.getSession().delete(t);
	}
	
	public void update(T t)
	{
		HibernateUtil.getSession().update(t);
	}
	
	public void saveOrUpdate(T t)
	{
		HibernateUtil.getSession().saveOrUpdate(t);
	}
}
