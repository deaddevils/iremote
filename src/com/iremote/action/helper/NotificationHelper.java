package com.iremote.action.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.common.jms.vo.*;
import com.iremote.common.mail.MailInterface;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.common.push.PushMessage;
import com.iremote.common.sms.SMSManageThread;
import com.iremote.common.sms.SMSVo;
import com.iremote.domain.*;
import com.iremote.infraredtrans.NorthAmericanUserNotificationSender;
import com.iremote.service.*;
import com.iremote.thirdpart.tecus.event.PushDSCKeyAlarmMessage;
import com.iremote.vo.NotifyUserList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

public class NotificationHelper {
	private static Log log = LogFactory.getLog(NotificationHelper.class);

	public static void pushmessage(CameraEvent n)
	{
		pushmessage(n , null , n.getCameraid());
	}

	public static void pushmessage(ZWaveDeviceEvent n)
	{
		pushmessage( n , n.getZwavedeviceid() , null);
	}

	public static void pushmessage(ZWaveDeviceEvent n, JSONObject json){

		RemoteService rs = new RemoteService();
		Integer phoneuserid = rs.queryOwnerId(n.getDeviceid());
		if ( phoneuserid == null )
			return ;

		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(phoneuserid);
		if ( pu == null )
			return ;
//		int platform = pu.getPlatform();

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(n.getDeviceid(),
				n.getZwavedeviceid()), Utils.getRemotePlatform(n.getDeviceid()), json);
//		PushMessage.pushWarningMessage(Arrays.asList(pu.getAlias()), platform,json);
	}

	public static void pushmessage(IApplianceEvent n, Integer zwavedeviceid, Integer cameraid) {
		pushmessage(n, zwavedeviceid, cameraid, null);
	}


	public static void pushmessage(IApplianceEvent n , Integer zwavedeviceid , Integer cameraid, String type)
	{
		JSONObject json = (JSONObject) JSON.toJSON(n);
		json.put("type", StringUtils.isBlank(type) ? "warning" : type);
		json.put("reporttime", n.getEventtime());
		json.put("message", n.getEventtype());
		json.put("majortype", n.getMajortype());
		json.put("warningstatus", Utils.getJsonArrayLastValue(n.getWarningstatuses()));
		if ( n.getApptokenid() != null )
			json.put("tokenid", n.getApptokenid());

		removeZWaveDeviceEventParamter(json);

		int platform = Utils.getRemotePlatform(n.getDeviceid());
		RemoteService rs = new RemoteService();
		Integer phoneuserid = rs.queryOwnerId(n.getDeviceid());
		if ( phoneuserid == null )
			return ;

		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(phoneuserid);
		if ( pu == null )
			return ;
		platform = pu.getPlatform();

		String met = n.getEventtype();
		MessageParser mp = Utils.createWarningMessage(met,platform ,IRemoteConstantDefine.DEFAULT_LANGUAGE, n.getName());

		if ( mp == null )
		{
			PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(n.getDeviceid(),zwavedeviceid , cameraid), platform, json);
			return ;
		}

		List<Integer> pn = PhoneUserHelper.queryAuthorityPhoneuserid(phoneuserid);
		pn.addAll(PhoneUserHelper.queryPhoneuseridbyDeviceShare(n.getDeviceid(), zwavedeviceid , cameraid));

		NotificationSettingService nss = new NotificationSettingService();
		NotifyUserList nsl = nss.warningUsers(pn ,platform, n.getDeviceid());

		PushMessage.pushWarningMessage(pus.queryAlias(nsl.getMessageUser()), platform,json);
		List<PhoneUser> pul = pus.query(nsl.getNotificationUser());

		List<NotificationSetting> notificationSettings = nss.queryValidSetting(nsl.getNotificationUser(),IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION);
		Map<String , List<PhoneUser> > map = splituserbylanguage(pul);
		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		for ( String lg : map.keySet())
		{
			List<PhoneUser> lst = map.get(lg);
			MessageParser m = Utils.createWarningMessage(n.getEventtype(),platform ,lg, n.getName());
			for(PhoneUser user : lst){
				notificationSettingMap.put(user.getAlias(),null);
				for(NotificationSetting ns : notificationSettings){
					if(ns.getPhoneuserid() == user.getPhoneuserid()){
						notificationSettingMap.put(user.getAlias(),ns);
					}
				}
			}

			PushMessage.pushWarningNotification(notificationSettingMap,platform, m.getMessage() , json);
		}

		pul = pus.query(nsl.getSmsUser());
		map = splituserbylanguage(pul);
		for ( String lg : map.keySet())		{
			sendSMS(map.get(lg) , Utils.createWarningMessage(n.getEventtype(),platform ,lg, n.getName()));
		}
		pul = pus.query(nsl.getMailUser());
		map = splituserbylanguage(pul);
		for ( String lg : map.keySet())		{
			sendMail(map.get(lg) , Utils.createWarningMessage(n.getEventtype(),platform ,lg, n.getName()));
		}

	}

	public static void pushmessage(Notification n , int phoneuserid, String devicename)
	{
		JSONObject json = (JSONObject) JSON.toJSON(n);
		json.put("type", "warning");
		removeNotificationParamter(json);

		int platform = Utils.getRemotePlatform(n.getDeviceid());
		PhoneUserService pus = new PhoneUserService();
		PhoneUser user = pus.query(phoneuserid);
		if ( user != null  )
			platform = user.getPlatform();

		String met = n.getMessage();
		MessageParser m = Utils.createWarningMessage(met,platform ,IRemoteConstantDefine.DEFAULT_LANGUAGE, devicename);

		if ( m == null || n.getStatus() != IRemoteConstantDefine.NOTIFICATION_STATUS_USER_ARM )
		{
			PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(n.getDeviceid(), n.getZwavedeviceid()), platform, json);
			return ;
		}

		List<Integer> pn = PhoneUserHelper.queryAuthorityPhoneuserid(phoneuserid);
		pn.addAll(PhoneUserHelper.queryPhoneuseridbyDeviceShare(n.getDeviceid() , n.getZwavedeviceid()));

		NotificationSettingService nss = new NotificationSettingService();
		NotifyUserList nsl = nss.warningUsers(pn ,platform, n.getDeviceid());

		PushMessage.pushWarningMessage(pus.queryAlias(nsl.getMessageUser()), platform,json);

		List<PhoneUser> pul = pus.query(nsl.getNotificationUser());
		List<NotificationSetting> notificationSettings = nss.queryValidSetting(nsl.getNotificationUser(),IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION);
		Map<String , List<PhoneUser> > map = splituserbylanguage(pul);
		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();

		for ( String lg : map.keySet())
		{
			List<PhoneUser> lst = map.get(lg);
			m = Utils.createWarningMessage(n.getMessage(),platform ,lg, devicename);
			for(PhoneUser pu : lst){
				notificationSettingMap.put(pu.getAlias(),null);
				for(NotificationSetting ns : notificationSettings){
					if(ns.getPhoneuserid() == pu.getPhoneuserid()){
						notificationSettingMap.put(pu.getAlias(),ns);
					}
				}
			}
			PushMessage.pushWarningNotification(notificationSettingMap,platform, m.getMessage() , json);
		}

		pul = pus.query(nsl.getSmsUser());
		map = splituserbylanguage(pul);
		for ( String lg : map.keySet())		{
			sendSMS(map.get(lg) , Utils.createWarningMessage(n.getMessage(),platform ,lg, devicename));
		}
		pul = pus.query(nsl.getMailUser());
		map = splituserbylanguage(pul);
		for ( String lg : map.keySet())		{
			sendMail(map.get(lg) , Utils.createWarningMessage(n.getMessage(),platform ,lg, devicename));
		}
	}

    public static void pushTypeMessage(ZWaveDeviceEvent zde, PhoneUser user, String name, String type){
		JSONObject json = new JSONObject();
		json.put("type", zde.getEventtype());
		json.put("zwavedeviceid", zde.getZwavedeviceid());
		json.put("reporttime", zde.getEventtime());

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(zde.getDeviceid(),
				zde.getZwavedeviceid()),Utils.getRemotePlatform(zde.getDeviceid()),json);
	}

	public static void pushTypeNotification(ZWaveDeviceEvent zde, PhoneUser user, String name, String type){
		JSONObject json = new JSONObject();
		json.put("type", zde.getEventtype());
		json.put("zwavedeviceid", zde.getZwavedeviceid());
		json.put("reporttime", zde.getEventtime());

		MessageParser m = Utils.createWarningMessage(type, user.getPlatform(),user.getLanguage(), name);

		NotificationSettingService nss = new NotificationSettingService();
		List<NotificationSetting> lst = nss.queryValidSetting(user.getPhoneuserid());
		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
		notificationSettingMap.put(user.getAlias(),null);
		if ( lst != null && lst.size() != 0 )
		{
			List<NotificationSetting> notificationSettings = nss.queryValidSetting(user.getPhoneuserid());
			if ( notificationSettings != null && notificationSettings.size() != 0 )
			{
				for (NotificationSetting ns : notificationSettings)
				{
					if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_SMS ) {
						sendSMS(Arrays.asList(new PhoneUser[]{user}), m);
					}else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION ){
						notificationSettingMap.put(user.getAlias(),ns);
					}
					else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_MAIL){
						sendMail(Arrays.asList(new PhoneUser[]{user}) , m);
					}
				}
			}
		}
		PushMessage.pushWarningNotification(notificationSettingMap ,user.getPlatform(),m.getMessage() , json);

	}

	public static void pushArmFailedMessage(PhoneUser user , String name)
	{
		JSONObject json = new JSONObject();
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_ARM_FAILED);
		removeNotificationParamter(json);

		MessageParser m = Utils.createWarningMessage(IRemoteConstantDefine.WARNING_TYPE_ARM_SUCCESS_DEVICE_FAILED,user.getPlatform(),user.getLanguage(), name);

		NotificationSettingService nss = new NotificationSettingService();
		List<NotificationSetting> lst = nss.queryValidSetting(user.getPhoneuserid());
		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
		notificationSettingMap.put(user.getAlias(),null);
		if ( lst != null && lst.size() != 0 )
		{
			List<NotificationSetting> notificationSettings = nss.queryValidSetting(user.getPhoneuserid());
			if ( notificationSettings != null && notificationSettings.size() != 0 )
			{
				for (NotificationSetting ns : notificationSettings)
				{
					if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_SMS ) {
						sendSMS(Arrays.asList(new PhoneUser[]{user}), m);
					}else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION ){
						notificationSettingMap.put(user.getAlias(),ns);
					}
					else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_MAIL){
						sendMail(Arrays.asList(new PhoneUser[]{user}) , m);
					}
				}
			}
		}
		PushMessage.pushWarningNotification(notificationSettingMap ,user.getPlatform(),m.getMessage() , json);

	}

	public static void pushArmFailedMessage(PhoneUser user , String name,String info)
	{
		if(!"dscsuccessdevicefailed".equals(info))
			return;
		JSONObject json = new JSONObject();
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_ARM_FAILED2);
		removeNotificationParamter(json);

		MessageParser m = Utils.createWarningMessage(IRemoteConstantDefine.WARNING_TYPE_ARM_FAILED2,user.getPlatform(),user.getLanguage(), name);

		NotificationSettingService nss = new NotificationSettingService();
		List<NotificationSetting> lst = nss.queryValidSetting(user.getPhoneuserid());
		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
		notificationSettingMap.put(user.getAlias(),null);
		if ( lst != null && lst.size() != 0 )
		{
			List<NotificationSetting> notificationSettings = nss.queryValidSetting(user.getPhoneuserid());
			if ( notificationSettings != null && notificationSettings.size() != 0 )
			{
				for (NotificationSetting ns : notificationSettings)
				{
					if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_SMS ) {
						sendSMS(Arrays.asList(new PhoneUser[]{user}), m);
					}else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION ){
						notificationSettingMap.put(user.getAlias(),ns);
					}
					else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_MAIL){
						sendMail(Arrays.asList(new PhoneUser[]{user}) , m);
					}
				}
			}
		}
		PushMessage.pushWarningNotification(notificationSettingMap ,user.getPlatform(),m.getMessage() , json);
	}

	public static void pushGooutMessage(PhoneUser user, String devicename)
	{
		JSONObject json = new JSONObject();
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_GOOUT_WARNING);
		removeNotificationParamter(json);

		MessageParser m = Utils.createWarningMessage(IRemoteConstantDefine.WARNING_TYPE_GOOUT_WARNING,user.getPlatform(),user.getLanguage(), devicename);

		NotificationSettingService nss = new NotificationSettingService();
		List<NotificationSetting> lst = nss.queryValidSetting(user.getPhoneuserid());

		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
		notificationSettingMap.put(user.getAlias(),null);

		if ( lst != null && lst.size() != 0 )
		{
			for (NotificationSetting ns : lst)
			{
				if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_SMS ) {
					sendSMS(Arrays.asList(new PhoneUser[]{user}), m);
				}else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION ){
					notificationSettingMap.put(user.getAlias(),ns);
				}
				else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_MAIL){
					sendMail(Arrays.asList(new PhoneUser[]{user}) , m);
				}
			}
		}
		PushMessage.pushWarningNotification(notificationSettingMap ,user.getPlatform(),m.getMessage() , json);
	}

	public static void pushWarningNotification(ZWaveDeviceEvent n , String devicename)
	{
		pushWarningNotification(n ,n.getZwavedeviceid() , null , devicename);
	}

	public static void pushWarningNotification(CameraEvent n , String devicename)
	{
		pushWarningNotification(n ,null , n.getCameraid() , devicename);
	}

	public static void pushElectricFenceNotifaction(IApplianceEvent n , Integer zwavedeviceid , Integer cameraid , String devicename)
	{
		JSONObject json = (JSONObject) JSON.toJSON(n);
		json.put("type", n.getEventtype());
		json.put("reporttime", n.getEventtime());
		json.put("message", n.getEventtype());
		json.put("majortype",  n.getMajortype());
		json.put("warningstatus", Utils.getJsonArrayLastValue(n.getWarningstatuses()));
		if ( n.getApptokenid() != null )
			json.put("tokenid", n.getApptokenid());
		removeZWaveDeviceEventParamter(json);

		int phoneuserid = readPhoneuserid(n.getDeviceid());
		List<Integer> pn = PhoneUserHelper.queryAuthorityPhoneuserid(phoneuserid);
		pn.addAll(PhoneUserHelper.queryPhoneuseridbyDeviceShare(n.getDeviceid() , zwavedeviceid , cameraid));

		PhoneUserService svr = new PhoneUserService();

		List<PhoneUser> pul = svr.query(pn);

		Map<String , List<PhoneUser> > map = splituserbylanguage(pul);

		int platfrom = Utils.getRemotePlatform(n.getDeviceid());
		if ( pul != null && pul.size() > 0 )
			platfrom = pul.get(0).getPlatform();

		NotificationSettingService nss = new NotificationSettingService();
		for ( String lg : map.keySet())
		{
			List<PhoneUser> lst = map.get(lg);

			MessageParser m = Utils.createWarningMessage(n.getEventtype(),platfrom,lg, devicename);

			Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
			for(PhoneUser phoneUser : lst){
				notificationSettingMap.put(phoneUser.getAlias(),null);
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

			PushMessage.pushWarningNotification(notificationSettingMap,platfrom, m.getMessage() , json);
			sendSMS(lst , m);
			sendMail(lst , m);
		}
	}

	public static void pushDSCKeyAlarmNotification(IApplianceEvent n, Integer zwavedeviceid){
		JSONObject json = (JSONObject) JSON.toJSON(n);
		json.put("type", n.getEventtype());
		json.put("reporttime", n.getEventtime());
		json.put("message", n.getEventtype());
		json.put("majortype",  n.getMajortype());
		json.put("deviceid", n.getDeviceid());
		json.put("warningstatuses", n.getWarningstatuses());
		removeZWaveDeviceEventParamter(json);

		int phoneuserid = readPhoneuserid(n.getDeviceid());
		if (phoneuserid == 0){
			log.info("phonuserid is 0, push interrupted.");
			return;
		}

		List<Integer> pn = PhoneUserHelper.queryAuthorityPhoneuserid(phoneuserid);
		pn.addAll(PhoneUserHelper.queryPhoneuseridbyDeviceShare(n.getDeviceid() , zwavedeviceid , null));

		PhoneUserService svr = new PhoneUserService();

		List<PhoneUser> pul = svr.query(pn);

		Map<String , List<PhoneUser> > map = splituserbylanguage(pul);

		int platfrom = Utils.getRemotePlatform(n.getDeviceid());
		if ( pul != null && pul.size() > 0 )
			platfrom = pul.get(0).getPlatform();

		NotificationSettingService nss = new NotificationSettingService();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		for ( String lg : map.keySet())
		{
			List<PhoneUser> lst = map.get(lg);

			MessageParser m = Utils.createWarningMessage(n.getEventtype(),platfrom,lg, n.getName());

			Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
			for(PhoneUser phoneUser : lst){
				notificationSettingMap.put(phoneUser.getAlias(),null);
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

			PushMessage.pushWarningNotification(notificationSettingMap, platfrom, m.getMessage(), json);
			sendSMS(lst , m);
			sendMail(lst , m);
		}
	}

	public static void pushWarningNotification(Remote remote, String eventType, String dest) {
		if (remote.getPhoneuserid() == null) {
			return;
		}
		JSONObject json = new JSONObject();
		json.put("deviceid", remote.getDeviceid());
		json.put("name", remote.getName());
		json.put("network", remote.getNetwork());
		json.put("networkintensity", remote.getNetworkintensity());
		json.put("reporttime", System.currentTimeMillis());
		json.put("type", eventType);
		json.put("pushtime", System.currentTimeMillis());

		List<Integer> pn = PhoneUserHelper.queryAuthorityPhoneuserid(remote.getPhoneuserid());

		PhoneUserService svr = new PhoneUserService();

		List<PhoneUser> pul = svr.query(pn);

		Map<String , List<PhoneUser> > map = splituserbylanguage(pul);

		int platfrom = Utils.getRemotePlatform(remote.getDeviceid());
		if ( pul != null && pul.size() > 0 )
			platfrom = pul.get(0).getPlatform();

		NotificationSettingService nss = new NotificationSettingService();
		for ( String lg : map.keySet())
		{
			List<PhoneUser> lst = map.get(lg);

			MessageParser m = Utils.createWarningMessage(eventType + dest,platfrom,lg, remote.getName());

			Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
			for(PhoneUser phoneUser : lst){
				notificationSettingMap.put(phoneUser.getAlias(),null);
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

			PushMessage.pushWarningNotification(notificationSettingMap, platfrom, m.getMessage(), json);
			sendSMS(lst , m);
			sendMail(lst , m);
		}
	}

	public static void pushWarningNotification(IApplianceEvent n , Integer zwavedeviceid , Integer cameraid , String devicename)
	{
		JSONObject json = (JSONObject) JSON.toJSON(n);
		json.put("type", "warning");
		json.put("reporttime", n.getEventtime());
		json.put("message", n.getEventtype());
		json.put("majortype",  n.getMajortype());
		if (n.getWarningstatus() != null)
			json.put("warningstatus", n.getWarningstatus());
		else
			json.put("warningstatus", Utils.getJsonArrayLastValue(n.getWarningstatuses()));

		if ( n.getApptokenid() != null )
			json.put("tokenid", n.getApptokenid());

		removeZWaveDeviceEventParamter(json);

		int phoneuserid = readPhoneuserid(n.getDeviceid());
		if (phoneuserid == 0){
			log.info("phonuserid is 0, push interrupted.");
			return;
		}

		List<Integer> pn = PhoneUserHelper.queryAuthorityPhoneuserid(phoneuserid);
		pn.addAll(PhoneUserHelper.queryPhoneuseridbyDeviceShare(n.getDeviceid() , zwavedeviceid , cameraid));

		PhoneUserService svr = new PhoneUserService();

		List<PhoneUser> pul = svr.query(pn);

		Map<String , List<PhoneUser> > map = splituserbylanguage(pul);

		int platfrom = Utils.getRemotePlatform(n.getDeviceid());
		if ( pul != null && pul.size() > 0 )
			platfrom = pul.get(0).getPlatform();

		NotificationSettingService nss = new NotificationSettingService();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		for ( String lg : map.keySet())
		{
			List<PhoneUser> lst = map.get(lg);

			MessageParser m = Utils.createWarningMessage(n.getEventtype(),platfrom,lg, devicename);

			Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
			for(PhoneUser phoneUser : lst){
				notificationSettingMap.put(phoneUser.getAlias(),null);
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

			PushMessage.pushWarningNotification(notificationSettingMap, platfrom, m.getMessage(), json);
			sendSMS(lst , m);
			sendMail(lst , m);
		}
	}

	public static void pushAlarmSmsOnBullyLockOpen(ZWaveDeviceEvent n , String devicename)
	{
		JSONObject json = n.getAppendmessage();

		if ( json == null)
			return ;

		List<DoorlockUser> lst   = new DoorlockUserService().queryValidUser(n.getZwavedeviceid(), json.getIntValue("usercode"),
				json.getIntValue("usertype"));

		if ( lst == null || lst.size() == 0 )
			return ;

		int platfrom = Utils.getRemotePlatform(n.getDeviceid());
		int phoneuserid = readPhoneuserid(n.getDeviceid());

		for ( DoorlockUser doorlockUser : lst )
		{
			List<Doorlockalarmphone> alarmPhoneList = new DoorlockalarmphoneService().querybydoorlockuser(doorlockUser);
			if(alarmPhoneList != null && alarmPhoneList.size() > 0){
				for(Doorlockalarmphone phone : alarmPhoneList){
					MessageParser message = Utils.createWarningMessage(n.getEventtype(),platfrom,getlanguagebyphoneuserid(phoneuserid), devicename);
					message.getParameter().put("username", json.getString("username"));
					SMSManageThread.addSMS(new SMSVo(phone.getCountrycode() , phone.getAlarmphone(), message ,platfrom ));
				}
			}
		}
	}

	public static String getlanguagebyphoneuserid(int phoneuserid){
		PhoneUserService phoneUserService = new PhoneUserService();
		PhoneUser phoneUser = phoneUserService.query(phoneuserid);
		if(phoneUser == null){
			return "zh_CN";
		}
		return phoneUser.getLanguage();
	}

	public static void pushWarningNotification(Notification n , String devicename)
	{
		pushWarningNotification(n , devicename , null );
	}

	public static void pushWarningNotification(Notification n , String devicename , Integer warningstatus)
	{
		pushWarningNotification( n , devicename , warningstatus , true);
	}

	public static void pushWarningNotification(Notification n , String devicename , Integer warningstatus, boolean sendsms){
		pushWarningNotification(n, devicename, warningstatus, sendsms, null);
	}

	public static void pushWarningNotification(Notification n , String devicename , Integer warningstatus, boolean sendsms, String message)
	{
		JSONObject json = (JSONObject) JSON.toJSON(n);
		json.put("type", "warning");
		if ( warningstatus != null )
			json.put("warningstatus", warningstatus);
		removeNotificationParamter(json);


		//String m = n.getMessage();
		//m = Utils.createWarningMessage(m,user.getLanguage(), devicename);

		int phoneuserid = readPhoneuserid(n.getDeviceid());
		List<Integer> pn = PhoneUserHelper.queryAuthorityPhoneuserid(phoneuserid);
		pn.addAll(PhoneUserHelper.queryPhoneuseridbyDeviceShare(n.getDeviceid(), n.getZwavedeviceid()));

		PhoneUserService svr = new PhoneUserService();

		List<PhoneUser> pul = svr.query(pn);

		Map<String , List<PhoneUser> > map = splituserbylanguage(pul);

		int platfrom = Utils.getRemotePlatform(n.getDeviceid());
		if ( pul != null && pul.size() > 0 )
			platfrom = pul.get(0).getPlatform();

		for ( String lg : map.keySet())
		{
			List<PhoneUser> lst = map.get(lg);

			MessageParser m = Utils.createWarningMessage(message != null ? message : n.getMessage(),platfrom,lg, devicename);
			NotificationSettingService nss = new NotificationSettingService();

			Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
			for(PhoneUser phoneUser : lst){
				notificationSettingMap.put(phoneUser.getAlias(),null);
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
			
			if ( sendsms )
				sendSMS(lst , m);
			sendMail(lst , m);
			PushMessage.pushWarningNotification(notificationSettingMap,platfrom, m.getMessage() , json);
		}
	}

	public static Collection<String> listPhoneUserAlias(List<PhoneUser> lst)
	{
		if ( lst == null )
			return null;
		Set<String> al = new HashSet<String>(lst.size());
		for ( PhoneUser pu : lst )
			al.add(pu.getAlias());
		return al;
	}

	public static Map<String , List<PhoneUser> > splituserbylanguage(List<PhoneUser> lst)
	{
		if ( lst == null )
			return null ;
		Map<String , List<PhoneUser> > map = new HashMap<String , List<PhoneUser> >();
		for ( PhoneUser p  : lst )
		{
			List<PhoneUser> l = map.get(p.getLanguage());
			if ( l == null )
			{
				l = new ArrayList<PhoneUser>();
				map.put(p.getLanguage(), l);
			}
			l.add(p);
		}
		return map ;
	}
	
	public static void pushSubDeviceStatus(ZWaveDeviceEvent device , Date reporttime , String operator )
	{
		JSONObject json = (JSONObject)JSON.toJSON(device);
		json.put("majortype", IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
		json.put("type", device.getEventtype());
		json.put("subdeviceid", device.getSubdeviceid());
		json.put("reporttime", Utils.formatTime(reporttime));
		json.put("warningstatus", Utils.getJsonArrayLastValue(device.getWarningstatuses()));
		json.put("channelid", device.getChannel());
		removeZWaveDeviceEventParamter(json);
		if ( operator != null && operator.length() > 0 )
			json.put("operator", operator);
		else
			json.put("operator", "");
		if ( device.getApptokenid() != null )
			json.put("tokenid", device.getApptokenid());

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(device.getDeviceid(), device.getZwavedeviceid()),Utils.getRemotePlatform(device.getDeviceid()), json);
	}

	public static void pushDeviceStatus(ZWaveDeviceEvent device , Date reporttime , String operator )
	{
		JSONObject json = (JSONObject)JSON.toJSON(device);
		json.put("majortype", IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS);
		json.put("reporttime", Utils.formatTime(reporttime));
		json.put("warningstatus", Utils.getJsonArrayLastValue(device.getWarningstatuses()));
		removeZWaveDeviceEventParamter(json);
		if ( operator != null && operator.length() > 0 )
			json.put("operator", operator);
		else
			json.put("operator", "");
		if ( device.getApptokenid() != null )
			json.put("tokenid", device.getApptokenid());

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(device.getDeviceid(), device.getZwavedeviceid()),Utils.getRemotePlatform(device.getDeviceid()), json);
	}

	public static void pushCameraStatus(CameraEvent device , Date reporttime , int platform)
	{
		JSONObject json = (JSONObject)JSON.toJSON(device);
		json.put("majortype", IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA);
		json.put("type", device.getEventtype());// IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS);
		json.put("reporttime", Utils.formatTime(reporttime));
		json.put("warningstatus", Utils.getJsonArrayLastValue(device.getWarningstatuses()));
		removeZWaveDeviceEventParamter(json);

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(device.getDeviceid(), null , device.getCameraid()),platform, json);
	}

	public static void pushDeviceStatus(ZWaveDevice device , Date reporttime , String operator )
	{
		JSONObject json = (JSONObject)JSON.toJSON(device);
		json.put("majortype", IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS);
		json.put("reporttime", reporttime);
		json.remove("associationscenelist");
		if ( operator != null && operator.length() > 0 )
			json.put("operator", operator);

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(device.getDeviceid(), device.getZwavedeviceid()),Utils.getRemotePlatform(device.getDeviceid()), json);
	}



	public static void pushBatteryStatus(ZWaveDeviceEvent device , Date reporttime )
	{
		JSONObject json = (JSONObject)JSON.toJSON(device);
		removeZWaveDeviceEventParamter(json);
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_BATTERY);
		json.put("reporttime", Utils.formatTime(reporttime));

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(device.getDeviceid(), device.getZwavedeviceid()),Utils.getRemotePlatform(device.getDeviceid()), json);
	}

	public static void pushDSCPartitionWarningStatus(DSCEvent device, Date reporttime) {
		JSONObject json = new JSONObject();
		json.put("partitionid", device.getPartitionid());
		json.put("reporttime", Utils.formatTime(reporttime));
		json.put("warningstatus", device.getWarningstatus());
		json.put("channelid", device.getChannelid());
		json.put("zwavedeviceid", device.getZwavedevceid());
		json.put("dscpartitionid", device.getDscparitionid());
		json.put("type", device.getType());

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(device.getDeviceid(),device.getZwavedevceid()),Utils.getRemotePlatform(device.getDeviceid()),json);
	}

	public static void pushDSCPartitionArmStatus(DSCEvent device, Date reporttime) {
		JSONObject json = new JSONObject();
		json.put("partitionid", device.getPartitionid());
		json.put("reporttime", Utils.formatTime(reporttime));
		json.put("armstatus", device.getArmstatus());
		json.put("type", device.getType());

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDevicetypeOrPhoneuserid(device.getDeviceid(),device.getZwavedevceid(),device.getPhoneuserid()),
				device.getRemotePlatform(),json);
	}


	public static void pushDSCPartitionArmStatusNotification(DSCEvent event, Date reporttime) {
		JSONObject json = new JSONObject();
		json.put("partitionid", event.getPartitionid());
		json.put("reporttime", Utils.formatTime(reporttime));
		json.put("armstatus", event.getArmstatus());
		json.put("type", event.getType());

		PartitionService ps = new PartitionService();
		Partition partition = null;
		if (event.getPartitionname() ==  null && event.getPartitionid() != 0) {
             partition = ps.query(event.getPartitionid());
            if (partition != null) {
                event.setPartitionname(partition.getName());
            }
        }

		if (event.getArmstatus() == IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM) {
        	return;
		}
		if (event.getArmstatus() != IRemoteConstantDefine.PARTITION_STATUS_OUT_HOME) {
        	event.setArmstatus(IRemoteConstantDefine.PARTITION_STATUS_IN_HOME);
		}

		PhoneUserService pus = new PhoneUserService();
		HashSet<Integer> phoneuserSet = new HashSet<>();

		if (event.getZwavedevceid() != 0 && event.getDeviceid() != null) {
			phoneuserSet.addAll(PhoneUserHelper.queryAuthorityByDeviceid(event.getDeviceid(), event.getZwavedevceid(), null));
		} else if (event.getPhoneuserid() != null && event.getPhoneuserid() != null) {
			//ignore dsc partition

            if (partition == null) {
                partition= ps.query(event.getPartitionid());
            }

            if (partition != null && partition.getPhoneuser() != null) {
                phoneuserSet.addAll(PhoneUserHelper.queryAuthorityPhoneuserid(partition.getPhoneuser().getPhoneuserid()));
            }
		}

		if (phoneuserSet.size() == 0) {
			log.info("phone user list are empty");
			return;
		}
		List<PhoneUser> phoneUserList = pus.query(phoneuserSet);

		Map<String, List<PhoneUser>> phoneUserListMap = splituserbylanguage(phoneUserList);
		Set<Map.Entry<String, List<PhoneUser>>> entrySet = phoneUserListMap.entrySet();

		for (Map.Entry<String, List<PhoneUser>> phoneUserListEntry : entrySet) {
			MessageParser mp = Utils.createWarningMessage(IRemoteConstantDefine.PARTITION_ARM_SUCCESS+event.getArmstatus() ,
					event.getRemotePlatform() , phoneUserListEntry.getValue().get(0).getLanguage(), event.getPartitionname());
			if (mp != null) {
				PushMessage.pushWarningNotification(phoneUserListEntry.getValue().stream().map(PhoneUser::getAlias).collect(toList()),
						event.getRemotePlatform(),mp.getMessage(), json);
			}
		}

	}


	public static void pushChannelStatus(SubdeviceStatusEvent sse, Date reporttime){
		JSONObject json = new JSONObject();
		json.put("type", sse.getType());
		json.put("subdeviceid", sse.getSubdeviceid());
		json.put("zwavedeviceid", sse.getZwavedeviceid());
		json.put("channelid", sse.getChannelid());
		json.put("enablestatus", sse.getEnablestatus());
		json.put("reporttime", Utils.formatTime(reporttime));
		json.put("warningstatuses", sse.getWarningstatuses());
		json.put("status", sse.getStatus());

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(sse.getDeviceid(),sse.getZwavedeviceid()),Utils.getRemotePlatform(sse.getDeviceid()),json);
	}

	public static void pushBatteryStatus(ZWaveDevice device , Date reporttime )
	{
		JSONObject json = (JSONObject)JSON.toJSON(device);
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_BATTERY);
		json.put("reporttime", Utils.formatTime(reporttime));
		json.remove("associationscenelist");

		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(device.getDeviceid(), device.getZwavedeviceid()),Utils.getRemotePlatform(device.getDeviceid()), json);
	}

	public static void pushHumidity(ZWaveDevice device , int humidity, Date reporttime )
	{
		JSONObject json = (JSONObject)JSON.toJSON(device);
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_HUMIDITY);
		json.put("reporttime", Utils.formatTime(reporttime));
		json.put("intparam", humidity);
		json.remove("associationscenelist");


		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(device.getDeviceid(),device.getZwavedeviceid()),Utils.getRemotePlatform(device.getDeviceid()), json);
	}

	public static void pushTemperature(ZWaveDevice device , int celsius , int fahrenheit , Date reporttime )
	{
		JSONObject json = (JSONObject)JSON.toJSON(device);
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_TEMPERATURE);
		json.put("reporttime", Utils.formatTime(reporttime));
		json.put("intparam", celsius);
		json.put("celsius", celsius);
		json.put("fahrenheit", fahrenheit);
		json.remove("associationscenelist");


		PushMessage.pushWarningMessage(PhoneUserHelper.queryAuthorityAliasByDeviceid(device.getDeviceid(),device.getZwavedeviceid()),Utils.getRemotePlatform(device.getDeviceid()), json);
	}

	public static void pushArmstatusChangeEvent(List<PhoneUser> lst , int platform , int armstatus)
	{
		JSONObject json = new JSONObject();
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_ARM_STATUS_CHANGE);
		json.put("armstatus", armstatus);

		PushMessage.pushWarningMessage(listPhoneUserAlias(lst) , platform, json);
	}
	public static void pushDeviceArmstatusChangeEvent(int armstatus , String deviceid , Integer zwavedeviceid , Integer cameraid)
	{
		JSONObject json = new JSONObject();
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_DEVICE_ARM_STATUS_CHANGE);
		json.put("armstatus", armstatus);
		if(zwavedeviceid != null && zwavedeviceid > 0)
			json.put("zwavedeviceid", zwavedeviceid);
		if(cameraid != null && cameraid > 0)
			json.put("cameraid", cameraid);

		int platfrom = Utils.getRemotePlatform(deviceid);
		RemoteService rs = new RemoteService();
		Integer phoneuserid = rs.queryOwnerId(deviceid);
		List<Integer> pn = PhoneUserHelper.queryAuthorityPhoneuserid(phoneuserid);
		pn.addAll(PhoneUserHelper.queryPhoneuseridbyDeviceShare(deviceid, zwavedeviceid , cameraid));

		PhoneUserService pus = new PhoneUserService();
		List<PhoneUser> pul = pus.query(pn);
		PushMessage.pushWarningMessage(listPhoneUserAlias(pul) , platfrom, json);
	}
	
	public static void pushDoorbellRingMessage(Notification n , PhoneUser user )
	{
		if ( user == null )
			return ;
		JSONObject json = (JSONObject)JSON.toJSON(n);
		removeNotificationParamter(json);
		json.put("type", IRemoteConstantDefine.WARNING_TYPE_DOOR_BELL_RING);

		MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.MESSGAE_TYPE_DOORBELL_RING ,user.getPlatform(), user.getLanguage());
		mp.getParameter().put("time", Utils.formatTime(n.getReporttime()));
		mp.getParameter().put("name", n.getName());
		String str = mp.getMessage();

		List<Integer> pn = PhoneUserHelper.querybySharetoPhoneuserid(user.getPhoneuserid());
		PhoneUserService pus = new PhoneUserService();
		List<PhoneUser> pul = pus.query(pn);

		NotificationSettingService nss = new NotificationSettingService();
		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
		for(PhoneUser pu : pul){
			List<NotificationSetting> notificationSettings = nss.queryValidSettingForDoorBell(user.getPhoneuserid());
			if ( notificationSettings != null && notificationSettings.size() != 0 )
			{
				for (NotificationSetting ns : notificationSettings)
				{
					if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_SMS ) {
						sendSMS(Arrays.asList(new PhoneUser[]{pu}), mp);
					}else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION ){
						notificationSettingMap.put(user.getAlias(),ns);
					}else if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_MAIL ){
						sendMail(Arrays.asList(new PhoneUser[]{pu}), mp);
					}
				}
			}
		}
		PushMessage.pushWarningNotification(notificationSettingMap,user.getPlatform(), str , json , IRemoteConstantDefine.SOUND_DOOR_BELL_RING);
	}

	public static void pushDoorbellRingMessage(Camera c , Date reporttime ,PhoneUser user )
	{
		if ( user == null )
			return ;

		JSONObject json = new JSONObject();
		json.put("type", "doorbellring");
		json.put("deviceid", c.getDeviceid());
		json.put("cameraid", c.getCameraid());
		json.put("name", c.getName());
		json.put("reporttime", reporttime.getTime());

		MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.MESSGAE_TYPE_DOORBELL_RING ,user.getPlatform(), user.getLanguage());
		mp.getParameter().put("time", Utils.formatTime(reporttime));
		mp.getParameter().put("name", c.getName());
		String str = mp.getMessage();

		List<Integer> pn = PhoneUserHelper.queryAuthorityPhoneuserid(user.getPhoneuserid());
		pn.addAll(PhoneUserHelper.queryPhoneuseridbyDeviceShare(c.getDeviceid(), null, c.getCameraid()));
		
		PhoneUserService pus = new PhoneUserService();
		List<PhoneUser> pul = pus.query(pn);
		NotificationSettingService nss = new NotificationSettingService();
		List<NotificationSetting> notificationSettings = nss.queryValidSettingForDoorBell(pn,IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION);

		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
		for(PhoneUser pu : pul){
			notificationSettingMap.put(pu.getAlias(),null);
			if ( notificationSettings != null && notificationSettings.size() != 0 )
			{
				for (NotificationSetting ns : notificationSettings)
				{
					if(pu.getPhoneuserid() == ns.getPhoneuserid()){
						notificationSettingMap.put(pu.getAlias(),ns);
					}
				}
			}
		}

		PushMessage.pushWarningNotification(notificationSettingMap,user.getPlatform(), str , json , IRemoteConstantDefine.SOUND_DOOR_BELL_RING);

	}

	@Deprecated
	public static void pushPeepholebellRingMessage(ZWaveDevice device ,  Date reporttime  , PhoneUser user )
	{
		JSONObject json = new JSONObject();

		json.put("type", IRemoteConstantDefine.WARNING_TYPE_PEEPHOLE_BELL_RING);
		json.put("deviceid", device.getDeviceid());
		json.put("nuid", device.getNuid());
		json.put("status", device.getStatus());
		json.put("reporttime", Utils.formatTime(reporttime));

		String str = String.format(MessageManager.getmessage(IRemoteConstantDefine.MESSGAE_TYPE_DOORBELL_RING ,user.getPlatform(), user.getLanguage()), Utils.formatTime(reporttime) , device.getName());

		List<Integer> pn = PhoneUserHelper.queryPhoneuseridByDeviceid(device.getDeviceid(), device.getZwavedeviceid());
		PhoneUserService pus = new PhoneUserService();
		NotificationSettingService nss = new NotificationSettingService();
		NotifyUserList nsl = nss.warningUsers(pn ,Utils.getRemotePlatform(device.getDeviceid()), device.getDeviceid());
		List<NotificationSetting> notificationSettings = nss.queryValidSetting(nsl.getNotificationUser(),IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION);
		List<PhoneUser> pul = pus.query(nsl.getNotificationUser());
        pul.addAll(pus.query(nsl.getMailUser()));
		Map<String , List<PhoneUser> > map = splituserbylanguage(pul);

		for ( String lg : map.keySet())
		{
			List<PhoneUser> lst = map.get(lg);
			Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
			for(PhoneUser pu : lst){
				notificationSettingMap.put(pu.getAlias(),null);
				for(NotificationSetting ns : notificationSettings){
					if(ns.getPhoneuserid() == pu.getPhoneuserid()){
						notificationSettingMap.put(pu.getAlias(),ns);
					}
				}
			}
			PushMessage.pushWarningNotification(notificationSettingMap,Utils.getRemotePlatform(device.getDeviceid()), str , json);
		}

	}


	public static void pushRemotePowertypeMessage(Remote remote)
	{
		if ( remote == null || remote.getPhoneuserid() == null )
			return ;
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(remote.getPhoneuserid());

		if ( pu == null )
			return ;

		JSONObject json = new JSONObject();
		json.put("type" , "remotepowertype");
		json.put("deviceid", remote.getDeviceid());
		json.put("powertype", remote.getPowertype());
		json.put("battery", remote.getBattery());
		json.put("reporttime", new Date());

		if ( remote.getPowertype() == IRemoteConstantDefine.REMOTE_POWER_TYPE_BATTERY
				&& !GatewayUtils.isCobbeLock(remote))
		{
			MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.WARNING_TYPE_REMOTE_BATTERY_POWERD, remote.getPlatform(), pu.getLanguage());
			mp.getParameter().put("name", remote.getName());
			mp.getParameter().put("battery", remote.getBattery());
			NotificationSettingService nss = new NotificationSettingService();
			List<NotificationSetting> notificationSettings = nss.queryValidSetting(pu.getPhoneuserid());
			Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
			notificationSettingMap.put(pu.getAlias(),null);

			if ( notificationSettings != null && notificationSettings.size() != 0 )
			{
				for (NotificationSetting ns : notificationSettings)
				{
					if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION ){
						notificationSettingMap.put(pu.getAlias(),ns);
					}
				}
			}
			PushMessage.pushWarningNotification(notificationSettingMap, remote.getPlatform(), mp.getMessage(), json);
		}
		else
			PushMessage.pushWarningMessage(Arrays.asList(pu.getAlias()), remote.getPlatform(), json);

	}

	public static String catDevicename(Remote remote , ZWaveDevice zwavedevice)
	{
		String dn = zwavedevice.getName() ;
		if ( dn == null )
			dn = " ";
		if ( remote != null && remote.getName() != null && remote.getName().length() > 0 )
			return String.format("%s(%s)", dn , remote.getName());
		return dn ;
	}

	private static int readPhoneuserid(String deviceid)
	{
		IremotepasswordService svr = new IremotepasswordService();
		Remote r = svr.getIremotepassword(deviceid);
		if ( r == null || r.getPhoneuserid() == null )
			return  0;
		return r.getPhoneuserid();
	}

	public static void sendSMS(List<PhoneUser> phoneuser , MessageParser message)
	{
		if ( phoneuser.size() == 0 || message == null )
			return ;

		sendSMS(phoneuser , message , "");

	}

	public static void sendMail(List<PhoneUser> phoneuser , MessageParser message)
	{
		if ( phoneuser.size() == 0 || message == null )
			return ;
		//List<String> mail = new ArrayList<>();
		List<String> settingmail = new ArrayList<>();
		NotificationSettingService nsss = new NotificationSettingService();
		for ( PhoneUser pu : phoneuser )
		{
			if ( pu.getUsertype() != IRemoteConstantDefine.PHONEUSER_USER_TYPE_MAIL)
				continue;
            //mail.add(pu.getMail());
            NotificationSetting query = nsss.query(pu.getPhoneuserid(), 5);
            if(query==null){
            	settingmail.add(pu.getMail());
            }else if(query!=null&&!StringUtils.isEmpty(query.getMail())){
            	settingmail.add(query.getMail());
            }
		}
		if(settingmail.size()==0)
			return;
        MailInterface.sendMail(settingmail,message.getMessageforLog(),message.getMessageforLog());

	}
	
	public static void sendSMS(List<PhoneUser> phonenumber , MessageParser message , String str )
	{
		if ( phonenumber.size() == 0 || message == null )
			return ;

		SmsHistoryService svr = new SmsHistoryService();
		for ( PhoneUser pu : phonenumber )
		{
			if ( pu.getUsertype() != IRemoteConstantDefine.PHONEUSER_USER_TYPE_NORMAL)
				continue;
			if ( pu.getSmscount() == null )
				pu.setSmscount(0);
			if ( pu.getSmscount() >= ServerRuntime.getInstance().getDefaultsmscount())
				continue;
			if ( pu.getSmscount() < ServerRuntime.getInstance().getDefaultsmscount()  )
				pu.setSmscount(pu.getSmscount()+1);

			SMSManageThread.addSMS(new SMSVo(pu.getCountrycode() , pu.getPhonenumber(), message ,pu.getPlatform() ));

			SmsHistory sh = new SmsHistory();
			sh.setMessage(message.getMessageforLog());
			sh.setCountrycode(pu.getCountrycode());
			sh.setPhonenumber(pu.getPhonenumber());
			sh.setPhoneuserid(pu.getPhoneuserid());

			svr.save(sh);

			if ( pu.getSmscount() == ServerRuntime.getInstance().getDefaultsmscount() )
				SMSManageThread.addSMS(new SMSVo(pu.getCountrycode() , pu.getPhonenumber(), MessageManager.getMessageParser(IRemoteConstantDefine.WARNING_TYPE_SMS_RUN_OUT, pu.getPlatform(), pu.getLanguage()),pu.getPlatform())) ;
			PushMessage.pushUserRemainingNumber(pu.getAlias() ,pu.getPlatform(), ServerRuntime.getInstance().getDefaultsmscount()  - pu.getSmscount() , ServerRuntime.getInstance().getDefaultcallcount() -  pu.getCallcount());
		}

	}

	public static void saveDefaultNotificationSetting(PhoneUser user)
	{
		NotificationSetting s = new NotificationSetting();
		s.setPhoneuserid(user.getPhoneuserid());
		s.setPhonenumber(user.getPhonenumber());
		s.setNotificationtype(4);
		s.setAthome(1);
		s.setStarttime("00:00");
		s.setEndtime("23:59");

		NotificationSettingService svr = new NotificationSettingService();
		svr.saveorUpdate(s);
	}

	private static void removeZWaveDeviceEventParamter(JSONObject json)
	{
		json.remove("channel");
		json.remove("taskIndentify");
		json.remove("eventtype");
		json.remove("report");
		//json.remove("applianceid");
		json.remove("taskKey");
		json.remove("shadowstatus");
	}

	private static void removeNotificationParamter(JSONObject json)
	{
		json.remove("status");
		json.remove("orimessage");
		json.remove("deleteflag");
		json.remove("notificationid");
		json.remove("eclipseby");
	}
}
