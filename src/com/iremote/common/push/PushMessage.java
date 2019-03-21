package com.iremote.common.push;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.push.vo.Notification;
import com.iremote.domain.NotificationSetting;
import com.iremote.domain.PhoneUser;
import com.iremote.service.NotificationSettingService;
import net.sf.json.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.common.push.vo.Extras;
import com.iremote.common.push.vo.Payload;

public class PushMessage {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(PushMessage.class);
	
	private static ExecutorService exeservice = Executors.newCachedThreadPool();
	
	private static final String INFO_CHANGED_MESSAGE = "";

	public static int pushShareRequestMessage(PhoneUser phoneUser , int platform, String message)
	{
		NotificationSettingService nss = new NotificationSettingService();
		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
		List<NotificationSetting> notificationSettings = nss.queryValidSetting(phoneUser.getPhoneuserid());
		notificationSettingMap.put(phoneUser.getAlias(),null);
		if ( notificationSettings != null && notificationSettings.size() != 0 )
		{
			for (NotificationSetting ns : notificationSettings)
			{
				if ( ns.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION ){
					notificationSettingMap.put(phoneUser.getAlias(),ns);
				}
			}
		}
		return pushNotification(notificationSettingMap, platform , message , IRemoteConstantDefine.NOTIFICATION_SHARE_INVITATION) ;
	}

	public static int pushShareRequestMessage(String toalias , int platform, String message)
	{
		return pushNotification(new String[]{toalias}, platform , message , IRemoteConstantDefine.NOTIFICATION_SHARE_INVITATION) ;
	}
	
	public static int pushInfoChangedMessage(String[] alias, int platform )
	{
		return pushInfoChangedMessage(alias , null , null , platform);
	}
	
	public static int pushInfoChangedMessage(String[] alias, Integer tokenid , String action, int platform )
	{
		JSONObject extras = new JSONObject();
		
		extras.put("type", IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED);
		
		if ( tokenid != null )
			extras.put("tokenid", tokenid);
		if ( action != null )
			extras.put("action", action);
		
		Payload pl = PushHelper.createMessage(alias, INFO_CHANGED_MESSAGE, extras);
		return push(pl, platform);
	}
	
	public static int pushOnwerChangedMessage(PhoneUser phoneUser , int platform, String uuid , String name , String language)
	{
		MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED, platform , language);
		mp.getParameter().put("name", name);
		if ( StringUtils.isBlank(name))
			mp.getParameter().put("name", uuid);

		NotificationSettingService nss = new NotificationSettingService();
		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
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
		return pushNotification(notificationSettingMap , platform, mp.getMessage() , IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED) ;
	}
	
	public static int pushShareRequestAcceptedMessage(String alias, int platform , int shareid)
	{
		Extras ex = PushHelper.createExtras(IRemoteConstantDefine.NOTIFICATION_SHARE_INVITATION_ACCEPTED);
		ex.setShareid(shareid);
		Payload pl = PushHelper.createMessage(new String[]{alias}, "", ex);
	
		return push(pl, platform);
	}
	
	public static int pushShareRequestRejectedMessage(String alias, int platform , int shareid )
	{
		Extras ex = PushHelper.createExtras(IRemoteConstantDefine.NOTIFICATION_SHARE_INVITATION_REJECTED);
		ex.setShareid(shareid);
		Payload pl = PushHelper.createMessage(new String[]{alias}, "", ex);
	
		return push(pl, platform);
	}
	
	public static int pushShareRequestDeleteMessage(String alias, int platform , int shareid )
	{
		Extras ex = PushHelper.createExtras(IRemoteConstantDefine.NOTIFICATION_SHARE_INVITATION_DELETE);
		ex.setShareid(shareid);
		Payload pl = PushHelper.createMessage(new String[]{alias}, "", ex);
	
		return push(pl, platform);
	}

	public static void pushSoundMessage(String alias, int platform , String message,int builder_id,String sound)
	{
		if (StringUtils.isEmpty(alias))
			return ;
		String[] al = new String[]{alias};
		JSONObject extras = new JSONObject();
		extras.put("message",message);
		extras.put("type",IRemoteConstantDefine.NOTIFICATION_SCENE_EXECUTE);
		extras.put("reporttime", System.currentTimeMillis()/1000);

		NotificationSetting ns = new NotificationSetting();
		ns.setBuilder_id(builder_id);
		ns.setSound(sound);
		ns.setApp(1);
		Map<String,NotificationSetting> notificationSettingMap = new HashMap<>();
		notificationSettingMap.put(alias,ns);

		List<Payload> pl = PushHelper.createNotification(notificationSettingMap, message, extras);
		for(Payload p : pl){
			//p.getOptions().setApns_production(false);
			push(p, platform);
		}
	}
	
	//self define message , not show in phone message bar .
	public static void pushWarningMessage(Collection<String> alias, int platform , JSONObject extras)
	{
		if ( alias == null || alias.size() == 0)
			return ;
		
		Payload pl = PushHelper.createMessage(alias.toArray(new String[0]), "", extras);
		push(pl, platform);
	}
	
	//notification will show in phone message bar .
	public static void pushWarningNotification(Map<String,NotificationSetting> notificationSettingMap, int platform , String message , JSONObject extras , String sound)
	{
		if ( notificationSettingMap == null || notificationSettingMap.size() == 0)
			return ;

		if ( StringUtils.isNotBlank(sound))
			extras.put("sound", sound);
		List<Payload> pl = PushHelper.createNotification(notificationSettingMap, message, extras);
		for(Payload p : pl){
			if ( p != null && p.getNotification() != null && p.getNotification().getIos() != null 
					&& StringUtils.isNotBlank(sound))
				p.getNotification().getIos().setSound(sound);
			push(p, platform);
		}
	}
	
	public static void pushWarningNotification(Map<String,NotificationSetting> notificationSettingMap, int platform , String message ,  JSONObject extras)
	{
		if ( notificationSettingMap == null || notificationSettingMap.size() == 0)
			return ;
		List<Payload> pl = PushHelper.createNotification(notificationSettingMap, message, extras);
		for(Payload p : pl){
			push(p, platform);
		}
	}
	
	public static void pushWarningNotification(Collection<String> alias, int platform , String message ,  JSONObject extras)
	{
		if ( alias == null || alias.size() == 0)
			return ;

		Payload pl = PushHelper.createNotification(alias.toArray(new String[0]), message, extras);
		push(pl, platform);
	}
	
	
	public static void pushUserRemainingNumber(String alias, int platform , int smscount , int callcount)
	{
		if ( alias == null || alias.length() == 0)
			return ;

		JSONObject extras = new JSONObject();
		
		extras.put("type", "remainingnumber");
		extras.put("smsnumber", smscount);
		extras.put("callnumber", callcount);
		
		Payload pl = PushHelper.createMessage(new String[]{alias}, "", extras);
		push(pl, platform);
	}
	
	public static void pushPasswordChangedMessage(String alias , int platform , Integer tokenid  )
	{
		if ( alias == null || alias.length() == 0)
			return ;

		JSONObject extras = new JSONObject();
		
		extras.put("type", "passwordchanged");
		extras.put("reporttime", System.currentTimeMillis()/1000);
		extras.put("tokenid", tokenid);
		
		Payload pl = PushHelper.createMessage(new String[]{alias}, "", extras);
		push(pl, platform);
	}
	
	public static void pushPhoneuserAttributeChangedMessage(String alias , int platform , String code , String value)
	{
		if ( alias == null || alias.length() == 0)
			return ;

		JSONObject extras = new JSONObject();
		
		extras.put("type", "phoneuserattributechanged");
		extras.put("code", code);
		extras.put("value", value);
		
		Payload pl = PushHelper.createMessage(new String[]{alias}, "", extras);
		push(pl, platform);
	}
	
	private static int pushMessage(String[] alias, int platform , String message , String type)
	{
		Payload pl = PushHelper.createMessage(alias, message, PushHelper.createExtras(type));
		return push(pl, platform);
	}

	private static int pushNotification(String[] alias, int platform , String message , String type)
	{
		Payload pl = PushHelper.createNotification(alias, message, PushHelper.createExtras(type));

		return push(pl, platform);
	}
	
	private static int pushNotification(Map<String,NotificationSetting> notificationSettingMap, int platform , String message , String type)
	{
		if ( notificationSettingMap == null || notificationSettingMap.size() == 0)
			return 0;

		List<Payload> pl = PushHelper.createNotification(notificationSettingMap, message, PushHelper.createExtras(type));
		for(Payload p : pl){
			push(p, platform);
		}

		return notificationSettingMap.size();
	}
	
	private static int push(Payload pl , int platform)
	{
		if ( pl.getAudience() == null )
			 return ErrorCodeDefine.TARGET_NOT_EXSIT;
		if ( ( pl.getAudience().getAlias() == null || pl.getAudience().getAlias().length == 0 )
				&& ( pl.getAudience().getTag() == null || pl.getAudience().getTag().length == 0 ))
				return ErrorCodeDefine.TARGET_NOT_EXSIT;
		
		putpushtime(pl);
		//putSoundAndroid(pl);
		exeservice.execute(new PushMessageThread(pl , platform));
		return 0;
	}

	@Deprecated
	private static void putSoundAndroid(Payload pl) {
		if (pl.getNotification() != null && pl.getNotification().getAndroid() != null) {
			int builderId = pl.getNotification().getAndroid().getBuilder_id();

			Object extras = pl.getNotification().getAndroid().getExtras();

			if (extras == null) {
				return;
			}

			String sound = "ring" + builderId +".mp3";

			if (builderId != 0) {
				if (extras instanceof JSONObject ){
					((JSONObject)extras).put("sound", sound);
				}
				if (extras instanceof Extras ){
					((Extras)extras).setSound(sound);
				}
			}
		}
	}

	private static void putpushtime(Payload pl)
	{
		long time = System.currentTimeMillis();
		
		if ( pl.getMessage() != null )
			putpushtime(pl.getMessage().getExtras(),time);
		if ( pl.getNotification() != null )
		{
			if ( pl.getNotification().getAndroid() != null )
				putpushtime(pl.getNotification().getAndroid().getExtras(),time);
			if ( pl.getNotification().getIos() != null )
				putpushtime(pl.getNotification().getIos().getExtras(),time);
		}
	}
	
	private static void putpushtime(Object extra , long time)
	{
		if ( extra == null )
			return ;
		
		if ( extra instanceof JSONObject )
			((JSONObject)extra).put("pushtime", time);
		if ( extra instanceof Extras )
			((Extras)extra).setReporttime(time);
	}
	
	public static void shutdown()
	{
		exeservice.shutdownNow();
	}
	
}


