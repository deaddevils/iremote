package com.iremote.common.sms;

import java.util.LinkedHashMap;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageParser;
import com.iremote.domain.Notification;
import com.iremote.test.db.Db;
import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;

public class SMSInterfaceTest {

	public static void main(String arg[])
	{
		MessageParser mp = new MessageParser("您获得授权，从2017-06-22 16:00:00到2017-06-24 16:00:00，您可以使用链接https://iremote.isurpass.com.cn/iremote/device/adop，打开908的门锁，开锁验证码是wi24s554u" , null , null);

		SMSInterface.sendSMS("86", "013502876070", mp, 4);
	}
	 
//	public static void main(String arg[])
//	{
//		Db.init();
//		SMSManageThread.start();
//		
//		Notification n = new Notification();
//		n.setDeviceid("iRemote2005000000720");
//		n.setMessage(IRemoteConstantDefine.WARNING_TYPE_LOW_BATTERY);
//		n.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_ARM);
//		
//		NotificationHelper.pushWarningNotification(n, "fds");
//		
//		Db.commit();
//	}
	
//	public static void main(String arg[])
//	{
		//16048078128 --Canada
		//61449298123  Australia  
		//SMSInterface.sendSMS("61", "61449298123", "\u6765\u81EA\u5C0F\u767D\u76D2\u5B50\u7684\u6D4B\u8BD5\u77ED\u4FE1\uFF0C\u6536\u5230\u540E\u8BF7\u8F6C\u544A\u767D\u603B\uFF0C\u591A\u8C22\u3002");
		//SMSInterface.sendSMS("1", "6048078128", "\u6765\u81EA\u5C0F\u767D\u76D2\u5B50\u7684\u6D4B\u8BD5\u77ED\u4FE1\uFF0C\u6536\u5230\u540E\u8BF7\u8F6C\u544A\u767D\u603B\uFF0C\u591A\u8C22\u3002");
//	}
	
/*
    public static void main(String[] args) {

        String authId = "MANZE3ODK1ZWEWMZE0ND";
        String authToken = "MzY5YWYzNWJkZDUxNjgyMjE4OWRiNTY3MmNhNDgw";

        RestAPI api = new RestAPI(authId, authToken, "v1");
        
        LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
        parameters.put("src", "13222223333"); // Sender's phone number with country code
        parameters.put("dst", "8613502876070"); // Receiver's phone number with country code
        //parameters.put("text", "Hi, from Plivo"); // Your SMS text message
        // Send Unicode text
        //parameters.put("text", "こんにちは、元気ですか？"); // Your SMS text message - Japanese中文测试短信
        //parameters.put("text", "Ce est texte généré aléatoirement"); // Your SMS text message - French
        parameters.put("text", "中文测试短信");
        parameters.put("url", "http://server/message/notification/"); // The URL to which with the status of the message is sent
        parameters.put("method", "GET"); // The method used to call the url
            
        try {
            // Send the message
            MessageResponse msgResponse = api.sendMessage(parameters);

            // Print the response
            System.out.println(msgResponse);
            // Print the Api ID
            System.out.println("Api ID : " + msgResponse.apiId);
            // Print the Response Message
            System.out.println("Message : " + msgResponse.message);

            if (msgResponse.serverCode == 202) {
                // Print the Message UUID
                System.out.println("Message UUID : " + msgResponse.messageUuids.get(0).toString());
            } else {
                System.out.println(msgResponse.error); 
            }
        } catch (PlivoException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    */
}
