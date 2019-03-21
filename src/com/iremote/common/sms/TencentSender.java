package com.iremote.common.sms;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.http.HttpUtil;
import com.iremote.common.message.MessageParser;

public class TencentSender implements ISmsSender
{
	private static Log log = LogFactory.getLog(TencentSender.class);
	
	private final String BASE_URL = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms?sdkappid=1400022505&random=";
	private final String APPKEY = "56b5858053990645786fc5a4a04104af";
	private final String smsSign;
	
	
	public TencentSender(String smsSign)
	{
		super();
		this.smsSign = smsSign;
	}

	protected String getSmsSign()
	{
		return smsSign;
	}
	
	@Override
	public int sendSMS(String countrycode, String phonenumber, MessageParser message)
	{
		long random = System.currentTimeMillis();
		
		String url = BASE_URL + String.valueOf(random);
		
		JSONObject json = new JSONObject();
		json.put("type", 0);
		json.put("msg", getSmsSign() + message.getMessage());
		json.put("time", random/1000);
		json.put("ext", String.valueOf(random));
		
		JSONObject tel = new JSONObject();
		tel.put("nationcode", countrycode);
		tel.put("mobile", phonenumber);
		
		json.put("tel", tel);
		
		String sha = String.format("appkey=%s&random=%d&time=%d&mobile=%s",APPKEY , random ,random/1000, phonenumber );
		
		json.put("sig", DigestUtils.sha256Hex(sha));
		
		
		String str = HttpUtil.httprequest(url, json.toString());
		
		if ( StringUtils.isNotBlank(str))
		{
			JSONObject rst = JSON.parseObject(str);
			if ( rst.containsKey("result") && rst.getIntValue("result") != ErrorCodeDefine.SUCCESS )
				log.error(StringEscapeUtils.unescapeJava(str));
		}
		
		return 0;
	}

}
