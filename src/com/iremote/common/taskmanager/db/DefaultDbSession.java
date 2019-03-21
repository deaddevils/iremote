package com.iremote.common.taskmanager.db;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.HibernateUtil;

public class DefaultDbSession implements IDbSession {

	@Override
	public void beginSession() 
	{
		HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
	}

	@Override
	public void beginTransaction() 
	{
		HibernateUtil.beginTransaction();
	}

	@Override
	public void commit() 
	{
		HibernateUtil.commit();
	}

	@Override
	public void rollback() 
	{
		HibernateUtil.rollback();
	}

	@Override
	public void closeSession() 
	{
		HibernateUtil.closeSession();
	}

}
