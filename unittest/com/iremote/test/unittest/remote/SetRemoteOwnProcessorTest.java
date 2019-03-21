package com.iremote.test.unittest.remote;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;


import com.iremote.test.db.Db;
import com.iremote.test.db.Env;

public class SetRemoteOwnProcessorTest {
	
	public static void main(String arg[])
	{
	}
	
	@Before
	public void setUp() throws ServletException
	{
		Env.getInstance().need();
		Db.init();
		//Db.update(ConstDefine_Lock.INIT_DOOR_LOCK_DEVICE, ConstDefine_Lock.INIT_DOOR_LOCK_DEVICE_FILTER);
		Db.commit();
		Db.init();
	}
	
	@After
	public void tearDown()
	{
		Db.commit();
	}
	
	
}
