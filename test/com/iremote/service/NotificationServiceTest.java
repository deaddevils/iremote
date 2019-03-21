package com.iremote.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.iremote.domain.Notification;
import com.iremote.test.db.Db;

public class NotificationServiceTest 
{
	public static void main(String arg[]) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		Db.init();
		NotificationService ns = new NotificationService();
		Notification n1 = ns.query(7750000);
		
		Notification n2 = new Notification();
		PropertyUtils.copyProperties(n2 , n1);
		
		n2.setNotificationid(0);
		
		ns.save(n2);;
		ns.saveByDeviceType(n2);
		
		Db.commit();
	}
}
