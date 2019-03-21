package com.iremote.device;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.device.OperateDevicebyGowildAction;
import com.iremote.common.http.HttpUtil;
import com.iremote.domain.PhoneUser;
import com.iremote.test.db.Db;

@SuppressWarnings("unused")
public class OperateDevicebyGowildActionTest {

	public static void main(String arg[])
	{
		JSONObject parameter = new JSONObject();
		//parameter.put("command", "{\"state\": \"STATE_ON\",\"name\":\"电视\"}");
		//parameter.put("command", "{\"state\": \"STATE_ON\",\"name\":\"空调\"}");
		//parameter.put("command", "{\"state\": \"STATE_OFF\",\"name\":\"空调\"}");
		//parameter.put("command", "{\"mode\": \"\u5236\u70ED\",\"name\":\"空调\"}");
		//parameter.put("command", "{\"action\":\"ACTION_TO\",\"attribute\":\"温度\",\"attributeValue\":\"19\",\"name\":\"空调\"}");
		//parameter.put("command", "{\"action\":\"ACTION_TO\",\"attributeValue\":\"21\",\"name\":\"空调\"}");
		//parameter.put("command", "{\"action\":\"ACTION_TO\",\"attribute\":\"频道\",\"attributeValue\":\"83\",\"name\":\"电视\"}");
		//parameter.put("command", "{\"action\":\"ACTION_TO\",\"attribute\":\"频道\",\"attributeValue\":\"83\",\"name\":\"机顶盒\"}");
		parameter.put("command", "{\"action\":\"ACTION_TO\",\"attribute\":\"节目\",\"attributeValue\":\"17\",\"name\":\"机顶盒\"}");
		
		parameter.put("Usertoken", "32c0ac3a195a45b28b17ac6ee6bdd874674191");

		//servertest(parameter);
		localtest(parameter);
		
	}
	
	private static void servertest(JSONObject parameter)
	{
		HttpUtil.httprequest("https://test.isurpass.com.cn/iremote/device/operationbygowild", parameter);
	}
	
	private static void localtest(JSONObject parameter)
	{
		Db.init();
		
		OperateDevicebyGowildAction action = new OperateDevicebyGowildAction();
		action.setCommand(parameter.getString("command"));

		PhoneUser pu = new PhoneUser();
		pu.setPhoneuserid(3);
		action.setPhoneuser(pu);
		
		action.execute();
		
		Db.commit();
	}
}
