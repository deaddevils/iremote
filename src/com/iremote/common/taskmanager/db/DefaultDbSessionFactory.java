package com.iremote.common.taskmanager.db;

public class DefaultDbSessionFactory implements IDbSessionFactory {

	@Override
	public IDbSession createDbSession() 
	{
		return new DefaultDbSession();
	}

}
