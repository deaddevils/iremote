package com.iremote.action.data;

import com.alibaba.fastjson.JSON;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.iremote.test.db.Db;

public class QueryDataAction2Test
{
	public static void main(String arg[])
	{
		Db.init();
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(100);
		
		QueryDataAction2 a = new QueryDataAction2();
		a.setPhoneuser(pu);
		
		a.execute();
		
		System.out.println(JSON.toJSONString(a));
		
	}
}
