package com.iremote.action.sms;

import com.alibaba.fastjson.JSON;
import com.iremote.test.db.Db;

public class RandcodeforRegisteActionTest
{
	public static void main(String arg[])
	{
		Db.init();
		
		RandcodeforRegisteAction action = new RandcodeforRegisteAction();
		action.setCountrycode("86");
		action.setPhonenumber("13502876070");
		action.setPlatform(0);
		
		action.execute();
		
		System.out.println(JSON.toJSONString(action));
		Db.commit();
	}
}
