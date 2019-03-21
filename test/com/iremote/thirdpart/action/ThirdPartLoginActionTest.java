package com.iremote.thirdpart.action;


import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.Hibernate.HibernateUtil;
@SuppressWarnings("unused")
public class ThirdPartLoginActionTest {

	public static void main(String arg[])
	{
		HibernateUtil.beginTransaction();
		ThirdPartLoginAction action = new ThirdPartLoginAction();
		action.setCode("thirdparter_zja");
		action.setPassword("b5d93a46162e4474b0576ddfcae3000a136377");
		action.execute();
		
		HibernateUtil.commit();
		
		System.out.println(JSONObject.toJSONString(action));
		
		HibernateUtil.closeSession();
	}
}
