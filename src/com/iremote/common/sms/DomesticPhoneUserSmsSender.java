package com.iremote.common.sms;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.message.MessageParser;

@Deprecated
public class DomesticPhoneUserSmsSender implements ISmsSender {
	
	private static Log log = LogFactory.getLog(DomesticPhoneUserSmsSender.class);
	
	private static final String username = "dljwzh00";
	private static final String password = "leDvTH18";
	private static final String scorpid = "1012818";
	
	private static String param = null ;
	private static final String suffix = "\u3010\u7BA1\u5BB6\u63D0\u9192\u3011";
	
	private static Pattern pattern = Pattern.compile("<State>[\\d]{1,}</State>");
	private static Pattern patternstate = Pattern.compile("[\\d]{1,}");
	
	static 
	{
		param = String.format("sname=%s&spwd=%s&scorpid=&sprdid=%s" , username , password , scorpid  );
	}
	
	@Override
	public int sendSMS(String countrycode, String phonenumber, MessageParser messageparser) 
	{
		String message = messageparser.getMessage();
		 if ( !message.endsWith(suffix))
			 message = message + suffix ;
		 try {
			 	String PostData = String.format("%s&sdst=%s&smsg=%s", param , phonenumber , java.net.URLEncoder.encode(message,"utf-8"));
				String ret = SMSInterface.sendSmsRequest(PostData, "http://cf.lmobile.cn/submitdata/Service.asmx/g_Submit");
			    log.info(ret);
			    return getResult(ret);
			} catch (UnsupportedEncodingException e) {
				log.error("", e);
			}
		return -1 ;
	}
	
	 private static int getResult(String result)
	 {
		 Matcher matcher = pattern.matcher(result);
		 if ( !matcher.find())
			 return -1 ;
		 result = matcher.group();
		 matcher = patternstate.matcher(result);
		 if ( !matcher.find())
			 return -1 ;
		 result = matcher.group();
		 return Integer.valueOf(result);
	 }

}
