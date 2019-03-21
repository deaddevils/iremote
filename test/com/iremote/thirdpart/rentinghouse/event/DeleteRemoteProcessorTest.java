package com.iremote.thirdpart.rentinghouse.event;

import java.util.Date;

import com.iremote.test.db.Db;

public class DeleteRemoteProcessorTest
{
	public static void main(String arg[])
	{
		DeleteRemoteProcessor drp = new DeleteRemoteProcessor();
		drp.setDeviceid("iRemote2005001000007");
		drp.setPhoneuserid(3521);
		drp.setEventtime(new Date());
		
		Db.init();
		drp.run();
		Db.commit();
	}
}
