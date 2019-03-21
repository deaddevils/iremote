package com.iremote.common.sms;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageParser;

public class SMSVo {

	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber ;
	private MessageParser message ;
	private int platform;
	private boolean sendoverseasms = false ;
	
	public SMSVo(String countrycode, String phonenumber, MessageParser message, int platform, boolean sendoverseasms)
	{
		super();
		this.countrycode = countrycode;
		this.phonenumber = phonenumber;
		this.message = message;
		this.platform = platform;
		this.sendoverseasms = sendoverseasms;
	}

	public SMSVo(String countrycode , String phonenumber, MessageParser message , int platform) {
		super();
		this.countrycode = countrycode ;
		this.phonenumber = phonenumber;
		this.message = message;
		this.platform = platform;
	}

	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public MessageParser getMessage() 
	{	
		return message;
	}
	public void setMessage(MessageParser message) {
		this.message = message;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public int getPlatform() {
		return platform;
	}

	public boolean isSendoverseasms()
	{
		return sendoverseasms;
	}

}
