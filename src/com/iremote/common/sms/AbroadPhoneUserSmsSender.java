package com.iremote.common.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.message.MessageParser;

@Deprecated
public class AbroadPhoneUserSmsSender implements ISmsSender {

	private static Log log = LogFactory.getLog(AbroadPhoneUserSmsSender.class);
	
	private static final String username2 = "JWZH_2015";
	private static final String password2 = "JWZH_2015";
	private static final String scorpid = "1012818";

	private static String param2 = null ;
	
	private static final String suffix = "\u3010\u7BA1\u5BB6\u63D0\u9192\u3011";
	
	static 
	{		
		param2 = String.format("src=%s&pwd=%s&servicesid=SEND" , username2 , password2 , scorpid  );
	}
	
	@Override
	public int sendSMS(String countrycode, String phonenumber, MessageParser messageparser) 
	{
		String message = messageparser.getMessage();
		 if ( !message.endsWith(suffix))
			 message = message + suffix ;
		 try {
			 	String PostData = String.format("%s&dest=%s%s&codec=%s&msg=%s", param2 , countrycode,phonenumber , 8 , encodeHexStr(8 , message));
				String ret = SMSInterface.sendSmsRequest(PostData, "http://210.51.190.233:8085/mt/MT3.ashx");
			    log.info(ret);
			    if ( ret == null || ret.startsWith("-"))
			    	return -1 ;
			    return 0 ;
			} catch (Throwable e) {
				log.error("", e);
			}
		return -1 ;
	}
	
	 public static final String bytesToHexString(byte[] bArray) {
	     StringBuffer sb = new StringBuffer(bArray.length);
	     String sTemp;
	     for (int i = 0; i < bArray.length; i++) {
	      sTemp = Integer.toHexString(0xFF & bArray[i]);
	      if (sTemp.length() < 2)
	       sb.append(0);
	      sb.append(sTemp.toUpperCase());
	     }
	     return sb.toString();
	 }  
	 
    public static String encodeHexStr(int dataCoding, String realStr) {
        String strhex = "";
        try {
             byte[] bytSource = null;
            if (dataCoding == 15) {
                bytSource = realStr.getBytes("GBK");
            } else if (dataCoding == 3) {
                bytSource = realStr.getBytes("ISO-8859-1");
            } else if (dataCoding == 8) {
                 bytSource = realStr.getBytes("UTF-16BE");
            } else {
                 bytSource = realStr.getBytes("ASCII");
            }
            strhex = bytesToHexString(bytSource);
            
        } catch (Exception e) {
        	log.error(e.getMessage() , e);
        }
        return strhex;
    }

}
