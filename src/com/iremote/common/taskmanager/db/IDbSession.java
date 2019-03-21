package com.iremote.common.taskmanager.db;

public interface IDbSession 
{
	void beginSession();
	void beginTransaction();
	void commit();
	void rollback();
	void closeSession();
}
