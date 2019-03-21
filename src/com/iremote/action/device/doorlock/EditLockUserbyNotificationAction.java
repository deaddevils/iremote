package com.iremote.action.device.doorlock;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.Notification;
import com.iremote.domain.notification.INotification;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.NotificationService;
import com.opensymphony.xwork2.Action;

public class EditLockUserbyNotificationAction
{
	private static Byte[] filter = new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1};
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int notificationid;
	private String username ;
	
	public String execute()
	{
		NotificationService ns = new NotificationService();
		Notification n = ns.query(notificationid);
		
		if ( n == null || n.getZwavedeviceid() == null || n.getZwavedeviceid() == 0 )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( StringUtils.isBlank(n.getOrimessage()))
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		JSONArray ja = JSON.parseArray(n.getOrimessage());
		
		byte[] b = new byte[ja.size()] ;
		for ( int i = 0 ; i < b.length ; i ++ )
			b[i] = ja.getByteValue(i);
		
		if ( !Utils.isByteMatch(filter, b))
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		int usercode = b[9] & 0xff ;
		int usertype = b[8] & 0xff ;
		
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.query(n.getZwavedeviceid(),usertype , usercode);
		
		if ( du != null )
			du.setUsername(username);

		n.setAppendmessage(username);
		
		INotification nt = ns.queryByDeviceType(notificationid, n.getMajortype(), n.getDevicetype());
		if ( nt == null )
			return Action.SUCCESS;
		nt.setAppendmessage(username);
		
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setNotificationid(int notificationid)
	{
		this.notificationid = notificationid;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	
}
