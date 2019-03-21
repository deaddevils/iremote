package com.iremote.common.taskmanager.db;

public class MockDbSessionFactory implements IDbSessionFactory {

	@Override
	public IDbSession createDbSession() {
		return new MockDbSession();
	}

}
