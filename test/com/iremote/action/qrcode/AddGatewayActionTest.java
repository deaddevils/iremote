package com.iremote.action.qrcode;

import com.alibaba.fastjson.JSON;
import com.iremote.service.PhoneUserService;
import com.iremote.test.db.Db;

import net.sf.json.JSONObject;

public class AddGatewayActionTest
{

	public static void main(String arg[])
	{
		Db.init();
		
		JSONObject am = new JSONObject();
		am.put("name", "test");
		
		JSONObject jm = new JSONObject();
		jm.put("type", "lockgateway");
		jm.put("qid", "JTYT7UMobKzsH34nnO0gNY");
		
		AddGatewayAction action = new AddGatewayAction();
		action.setAppendmessage(am.toString());
		action.setMessage(jm.toString());
		
		PhoneUserService pus = new PhoneUserService();
		
		action.setPhoneuser(pus.query(110));
		action.execute();
		
		System.out.println(JSON.toJSONString(action));
		Db.commit();
	} 
}
