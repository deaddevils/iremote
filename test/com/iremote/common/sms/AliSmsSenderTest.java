package com.iremote.common.sms;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class AliSmsSenderTest
{
	public static void main(String arg[])
	{
		AliSmsSender ass = new AliSmsSender();
		JSONObject json = new JSONObject();
		json.put("name", "¥Û√≈");
		//ass.sendSMS("86", "13502876070", "SMS_35235005", json);
	}
}
