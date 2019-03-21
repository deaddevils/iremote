package com.iremote.action.qrcode;

import com.iremote.domain.PhoneUser;

public interface IScanQrCodeProcessor
{
	void setMessage(String message);
	void setAppendmessage(String appendmessage);
	void setPhoneuser(PhoneUser phoneuser);
	void setTimezoneid(String timezoneid);
	String execute();
	int getResultCode();
}
