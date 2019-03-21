package com.iremote.domain;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.notification.Notification_1;
import com.iremote.test.db.Db;

public class NotificationTest 
{
	public static void main(String arg[])
	{
		Db.init();
		
		Notification_1 n1 = new Notification_1();
		n1.setPhoneuserid(1);
		n1.setDeviceid("fdklsjfdsa");
		n1.setZwavedeviceid(2);
		n1.setDevicetype("3");
		n1.setMajortype("zWave");
		
		HibernateUtil.getSession().save(n1);
		
		Db.commit();
	}
}
