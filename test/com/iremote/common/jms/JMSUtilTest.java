package com.iremote.common.jms;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.test.db.Db;

public class JMSUtilTest {

	public static void main(String arg[])
	{
		Db.init();
		JMSUtil.init();
		
		JMSUtil.sendmessage("test.foo", "0");
		JMSUtil.registMessageCosumer("test.foo", new TestMessageListener());
		JMSUtil.sendmessage("test.foo", "1");
		JMSUtil.sendmessage("test.foo", "2");
		JMSUtil.sendmessage("test.foo", "3");
		JMSUtil.sendmessage("test.foo", "4");
		JMSUtil.sendmessage("test.foo", "5");
		
		JMSUtil.commitmessage();
		
		JMSUtil.close();
		
		JMSUtil.init();
		
		JMSUtil.sendmessage("test.foo", "1");
		JMSUtil.sendmessage("test.foo", "2");
		JMSUtil.sendmessage("test.foo", "3");

		JMSUtil.commitmessage();
		JMSUtil.close();
		HibernateUtil.destroyall();
	}
}
