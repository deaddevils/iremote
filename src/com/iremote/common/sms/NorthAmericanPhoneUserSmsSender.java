package com.iremote.common.sms;

import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageParser;
import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;

public class NorthAmericanPhoneUserSmsSender implements ISmsSender {

	private static Log log = LogFactory.getLog(NorthAmericanPhoneUserSmsSender.class);

	private final String SRC_NUMBER  = "16044499815";
	private final String FOO_SRC_NUMBER = "19700101"; 
	
    private final String authId = "MANZE3ODK1ZWEWMZE0ND";
    private final String authToken = "MzY5YWYzNWJkZDUxNjgyMjE4OWRiNTY3MmNhNDgw";

	
	@Override
	public int sendSMS(String countrycode, String phonenumber, MessageParser messageparser) 
	{
		String message = messageparser.getMessage();
		RestAPI api = new RestAPI(getAuthId(), getAuthToken(), "v1");
		LinkedHashMap<String, String> parameter = new LinkedHashMap<String, String>();
		
		if ( IRemoteConstantDefine.INTERNATIONAL_DIALING_CODE_NORTH_AMERICA.equals(countrycode))
			parameter.put("src", getSRC_NUMBER());
		else 
			parameter.put("src", getFOO_SRC_NUMBER());
		parameter.put("dst", String.format("%s%s", countrycode , phonenumber));
		parameter.put("text", message);
		
        try {
            MessageResponse msgResponse = api.sendMessage(parameter);

            if ( log.isInfoEnabled())
            	log.info(JSON.toJSONString(msgResponse));

            if (msgResponse.serverCode == 202) 
            	return ErrorCodeDefine.SUCCESS ;
            
        } 
        catch (PlivoException e) 
        {
        	log.error(e.getMessage() , e);
        }
        
		return ErrorCodeDefine.UNKNOW_ERROR;
	}

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

}

