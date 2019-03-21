package com.iremote.common.Hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
@SuppressWarnings("unused")
public class SessionTrackerManagerTestThread implements Runnable {

	private int id ;
	
	public SessionTrackerManagerTestThread(int id) {
		super();
		this.id = id;
	}

	@Override
	public void run() {

		String threadid = String.format("%d ,%d , %s ", id,Thread.currentThread().getId() , Thread.currentThread().getName());
		System.out.println(threadid + "get session" );
		HibernateUtil.beginTransaction();
		System.out.println(threadid + "begin transaction" );
		
		try {
			Thread.sleep(2*60*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		HibernateUtil.commit();
		HibernateUtil.closeSession();
	}

}
