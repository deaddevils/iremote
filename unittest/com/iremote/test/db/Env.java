package com.iremote.test.db;

import com.iremote.interceptor.RemoteServer;

public class Env {

	private static Env instance = new Env();
	
	static 
	{
		RemoteServer rs = new RemoteServer();
		rs.readconfigure();
		rs.initJMS();
	}
	
	private Env()
	{
		
	}
	
	public static Env getInstance()
	{
		return instance;
	}
	
	public void need()
	{
		
	}
}
