package com.iremote.infraredtrans;

import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;

public interface INotificationSender {

	void pushmessage(Notification n, PhoneUser phoneuser, String devicename);
	void pushmovementmessage(Notification n, PhoneUser phoneuser, String devicename);
	void triggeralarm(PhoneUser phoneuser , Notification notification) ;
}
