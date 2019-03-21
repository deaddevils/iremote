package com.iremote.infraredtrans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iremote.domain.NotificationSetting;
import com.iremote.service.NotificationSettingService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.message.MessageParser;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;

@Deprecated
public class NorthAmericanUserNotificationSender implements INotificationSender {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(NorthAmericanUserNotificationSender.class);
	
	private JSONObject json;
	private int platform;
	private List<PhoneUser> phoneuserlst;
	private PhoneUser phoneuser;
	private Notification notification;
	private String devicename;
	private String message;
	
	@Override
	public void pushmessage(Notification n, PhoneUser phoneuser, String devicename) 
	{
		init(n, phoneuser , devicename);
		
		if ( message == null || message.length() == 0 
				|| n.getStatus() == IRemoteConstantDefine.NOTIFICATION_STATUS_USER_DISARM 
				|| n.getStatus() == IRemoteConstantDefine.NOTIFICATION_STATUS_DEVICE_DISABLE)
		{
			pushselfdefinemessage();
			return ;
		}
		
		pushnotification();
		
	}

	@Override
	public void pushmovementmessage(Notification n, PhoneUser phoneuser, String devicename) 
	{
		if ( n.getStatus() != IRemoteConstantDefine.NOTIFICATION_STATUS_USER_INHOME_ARM)
		{
			pushmessage(n , phoneuser , devicename);
			return ;
		}
		
		init(n, phoneuser , devicename);
		
		pushselfdefinemessage();
	}
	
	private void init(Notification n, PhoneUser phoneuser, String devicename)
	{
		this.notification = n ;
		this.devicename = devicename ;
		this.phoneuser = phoneuser;
		
		json = (JSONObject) JSON.toJSON(n);
		json.put("type", "warning");
		json.remove("status");
		
		platform = Utils.getRemotePlatform(n.getDeviceid());
		
		MessageParser mp = Utils.createWarningMessage(n.getMessage(),phoneuser.getPlatform(),IRemoteConstantDefine.DEFAULT_LANGUAGE, devicename);
		
		if ( mp != null )
			this.message = mp.getMessage();
		
		phoneuserlst = PhoneUserHelper.queryAuthorizedUser(phoneuser.getPhoneuserid());
		
		PhoneUserService pus = new PhoneUserService();
		
		phoneuserlst.addAll(pus.querybyfamiliyid(phoneuser.getFamilyid()));
	}

	private void pushselfdefinemessage()
	{
		PushMessage.pushWarningMessage(NotificationHelper.listPhoneUserAlias(phoneuserlst), platform, json);		
	}
	
	private void pushnotification()
	{
		Map<String , List<PhoneUser> > map = NotificationHelper.splituserbylanguage(phoneuserlst);
		NotificationSettingService nss = new NotificationSettingService();
		for ( String lg : map.keySet())
		{
			List<PhoneUser> lst = map.get(lg);
			MessageParser m = Utils.createWarningMessage(notification.getMessage(),phoneuser.getPlatform(),lg, devicename);

			Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
			for(PhoneUser phoneUser : lst){
				List<NotificationSetting> notificationSettings = nss.queryValidSetting(phoneUser.getPhoneuserid());
				if ( notificationSettings != null && notificationSettings.size() != 0 )
				{
					for (NotificationSetting ns : notificationSettings)
					{
						if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION ){
							notificationSettingMap.put(phoneUser.getAlias(),ns);
						}
					}
				}
			}
			PushMessage.pushWarningNotification(notificationSettingMap,platform, m.getMessage() , json);
			NotificationHelper.sendSMS(lst , m);
			NotificationHelper.sendMail(lst , m);
		}
		
		triggeralarm() ;
	}
	
	public void triggeralarm(PhoneUser phoneuser , Notification notification) 
	{
		this.phoneuser = phoneuser ;
		this.notification = notification ;
		triggeralarm();
	}

	private void triggeralarm() 
	{
		if ( phoneuser == null )
			return ;
		if ( notification == null || notification.getEclipseby() != 0 )
			return ;
		if (  notification.getStatus() == IRemoteConstantDefine.NOTIFICATION_STATUS_USER_DISARM 
				|| notification.getStatus() == IRemoteConstantDefine.NOTIFICATION_STATUS_DEVICE_DISABLE)
			return ;
		
		List<Integer> pidl;
		if ( phoneuser.getFamilyid() != null && phoneuser.getFamilyid() != 0 )
			pidl = PhoneUserHelper.queryPhoneuseridbyfamilyid(phoneuser.getFamilyid());
		else 
		{
			pidl = new ArrayList<Integer>(1);
			pidl.add(phoneuser.getPhoneuserid());
		}
		
		IremotepasswordService rs = new IremotepasswordService();
		List<String> didl = rs.queryDeviceidbyPhoneUserid(pidl);
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.querybydeviceid(didl);
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( ZWaveDevice d : lst )
		{
			if ( !IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(d.getDevicetype()))
				continue;
			CommandTlv ct = CommandUtil.createAlarmCommand(d.getNuid());
			SynchronizeRequestHelper.synchronizeRequest(d.getDeviceid(), ct , 0);

		}
		
	}
	
}
