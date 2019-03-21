package com.iremote.common.sms;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.message.MessageParser;

public class DomesticSmsDistribute implements ISmsSender
{

	@Override
	public int sendSMS(String countrycode, String phonenumber, MessageParser message)
	{
		if ( StringUtils.isNotBlank(message.getTemplatecode()) )
		{
			AliSmsSender as = new AliSmsSender();
			as.sendSMS(countrycode, phonenumber, message);
		}
		else 
		{
			DomesticPhoneUserSmsSender2 dss = new DomesticPhoneUserSmsSender2();
			dss.sendSMS(countrycode, phonenumber, message);
		}
		return 0;
	}

}
