package com.iremote.common.sms;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.Utils;
import com.iremote.common.http.HttpUtil;
import com.iremote.common.message.MessageParser;

public class DomesticPhoneUserSmsSender2 implements ISmsSender {
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DomesticPhoneUserSmsSender2.class);
	
	private static final String username = "jwzh00";
	private static final String password = "Bwc1109zHJW9129";
	private static final int scorpid = 147;
	
	private static final String suffix = "\u3010\u7BA1\u5BB6\u63D0\u9192\u3011";
	
	private static Pattern pattern = Pattern.compile("<returnstatus>([a-zA-Z]{1,})</returnstatus>");
	
	private static String URL = "http://hyt.uewang.net/v2sms.aspx";

	@Override
	public int sendSMS(String countrycode, String phonenumber, MessageParser messageparser){
		return sendSMS(countrycode,phonenumber,messageparser,"");
	}
	
	public int sendSMS(String countrycode, String phonenumber, MessageParser messageparser,String suff) {
		String message = messageparser.getMessage();
		 if(StringUtils.isNotBlank(suff)){
			message = message + suff ; 
		 }else{
			 if ( !message.endsWith(suffix))
				 message = message + suffix ;
		 }
		 JSONObject param  = new JSONObject();
		 String timestamp = Utils.formatTime(new Date(), "yyyyMMddHHmmss");
		 param.put("userid", scorpid);
		 param.put("timestamp", timestamp);
		 param.put("sign", DigestUtils.md5Hex(String.format("%s%s%s", username,password,timestamp)));
		 param.put("mobile", phonenumber);
		 param.put("content", message);
		 param.put("action", "send");
		
		 String ret = HttpUtil.httprequest(URL, param);
		 //log.info(ret);
		 return getResult(ret);
	}
	
	 private static int getResult(String result)
	 {
		 if ( result == null )
			 return -1 ;
		 Matcher matcher = pattern.matcher(result);
		 if ( !matcher.find())
			 return -1 ;
		 result = matcher.group();
		 if ("<returnstatus>Success</returnstatus>".equalsIgnoreCase(result))
			 return 0 ;
		 return -1 ;
	 }


}
