package com.iremote.common.sms;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.http.HttpUtil;
import com.iremote.common.message.MessageParser;

public class AliSmsSender implements ISmsSender
{
	
	@Override
	public int sendSMS(String countrycode, String phonenumber, MessageParser template)
	{
		JSONObject header = new JSONObject();
		header.put("Authorization", IRemoteConstantDefine.ALI_APPCODE);
		
		JSONObject ps = template.getParameter();
		for ( String key : ps.keySet())
		{
			String v = ps.getString(key);
			if ( StringUtils.isNotBlank(v) && v.length() > 10 )
				ps.put(key, JWStringUtils.abbreviate(v, 15));
		}
		
		JSONObject parameter = new JSONObject();
		parameter.put("ParamString", ps.toJSONString());
		parameter.put("RecNum", phonenumber);
		parameter.put("SignName", IRemoteConstantDefine.ALI_SMS_SIGNNAME);
		parameter.put("TemplateCode", template.getTemplatecode());
		HttpUtil.httpGet("http://sms.market.alicloudapi.com/singleSendSms", parameter, header);
		return 0;
	}

}
