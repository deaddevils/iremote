package com.iremote.action.notification;

import com.alibaba.fastjson.JSON;
import com.iremote.service.PhoneUserService;
import com.iremote.test.db.Db;

import net.sf.json.JSONObject;

public class QueryNotificationActionTest
{
	public static void main(String arg[])
	{
		Db.init();
		QueryNotificationAction a = new QueryNotificationAction();
		a.setDevicetype("1");
		
		PhoneUserService pus = new PhoneUserService();
		a.setPhoneuser(pus.query(597));
		
		a.execute();
		
		System.out.println(JSONObject.fromObject(a).toString());
		System.out.println();
		System.out.println(JSON.toJSONString(a));
		
		Db.rollback();
		
	}
}
