package com.iremote.common.Hibernate;

public class SessionTrackerManagerTest {

	public static void main(String arg[])
	{
		Thread tt = new Thread(SessionTrackerManager.getInstance());
		tt.start();
		for ( int i = 0 ; i < 20 ; i ++ )
		{
			Thread t = new Thread(new SessionTrackerManagerTestThread(i));
			t.start();
		}
		
		try {
			Thread.sleep(30 * 60 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
