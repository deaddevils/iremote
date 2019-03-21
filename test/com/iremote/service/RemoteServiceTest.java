package com.iremote.service;

import com.iremote.test.db.Db;

public class RemoteServiceTest {

	public static void main(String arg[])
	{
		Db.init();
		RemoteService rs = new RemoteService();
		System.out.println(rs.queryDeviceidByPhoneuserid(111));
	}
}
