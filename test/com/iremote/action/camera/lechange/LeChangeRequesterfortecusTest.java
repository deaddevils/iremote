package com.iremote.action.camera.lechange;

import com.alibaba.fastjson.JSONObject;

public class LeChangeRequesterfortecusTest extends LeChangeRequesterfortecus
{
	public static void main(String arg[])
	{
		LeChangeRequesterfortecusTest t = new LeChangeRequesterfortecusTest();
		JSONObject json = t.userBindSms("423366782");
		
		System.out.println(json.toJSONString());
	}
	
	private JSONObject userBindNoSms(String phonenumber)
	{
		JSONObject pm = new JSONObject();
		pm.put("phone", phonenumber);
		
		return leChangeRequest("userBindNoSms" , pm);
	}
}
