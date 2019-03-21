package com.iremote.action.camera.lechange;

import com.alibaba.fastjson.JSONObject;

public class LeChangeRequesterforjwzhTest 
{
	public static void main(String arg[])
	{
		LeChangeInterface lc = new LeChangeInterface("lc15d53ecab08a4b0b" , "22484dc0156b480f93e0e25cf483bc" , "a32284299ec24151");
		JSONObject json = lc.accessToken();
		String token = lc.getData(json, "accessToken");
		
		JSONObject para = new JSONObject();
		para.put("token", token);
		para.put("status", "on");
		para.put("callbackUrl", "http://iremote.isurpass.com.cn/iremote/camera/lechange/lechangewarningreport");
		para.put("callbackFlag", "alarm,deviceStatus");
		
		json = lc.leChangeRequest("setMessageCallback", para);
		
		System.out.println(json.toJSONString());
	}
}
