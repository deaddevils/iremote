package com.iremote.common.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.message.MessageParser;

public class AmetaPhoneUserSmsSender extends NorthAmericanPhoneUserSmsSender {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AmetaPhoneUserSmsSender.class);

	private final String SRC_NUMBER  = "6479519870";
	private final String FOO_SRC_NUMBER = "19700101"; 
	
    private final String authId = "MAYJRJMDK4YZI2NDE5ND";
    private final String authToken = "YTNlM2JhYWJiNWY5OGYwNTJjMzFlOTI3MzlmNjMy";

	public String getSRC_NUMBER() {
		return SRC_NUMBER;
	}

	public String getFOO_SRC_NUMBER() {
		return FOO_SRC_NUMBER;
	}

	public String getAuthId() {
		return authId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public static void main(String arg[])
	{
		MessageParser mp = new MessageParser("hello ameta" , null , null);
		
		AmetaPhoneUserSmsSender as = new AmetaPhoneUserSmsSender();
		as.sendSMS("+86", "13502876070", mp);
	}
}

