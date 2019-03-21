package com.iremote.common.sms;

import com.iremote.common.message.MessageParser;

public interface ISmsSender {

	int sendSMS(String countrycode ,String phonenumber , MessageParser message);
}
