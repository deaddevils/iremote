package com.iremote.domain;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.service.IremotepasswordService;

public class RemoteTest {

	public static void main(String arg[])
	{
		HibernateUtil.beginTransaction();
		
		RemoteTest t = new RemoteTest();
		t.timezonetest();
		
		HibernateUtil.commit();
		
		HibernateUtil.closeSession();
	}
	
	private void timezonetest()
	{
		IremotepasswordService svr = new IremotepasswordService();
		Remote r = svr.getIremotepassword("iRemote4005000000261");
		
		Timezone tz = new Timezone();
		tz.setZoneid("8");
		tz.setZonetext("fkdsf");
		r.setTimezone(tz);
		System.out.println(r.getTimezone().getZonetext());
		
	}
}
