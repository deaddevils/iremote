package com.iremote.common.push;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.push.vo.Audience;
import com.iremote.common.push.vo.Extras;
import com.iremote.common.push.vo.Message;
import com.iremote.common.push.vo.Notification;
import com.iremote.common.push.vo.Payload;
import com.iremote.domain.NotificationSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class PushHelper {

	//self define message , not show in phone message bar .
	public static Payload createMessage(String[] alias , String message , Object extras)
	{
		Payload pl = new Payload();
		
		pl.setAudience(creattAudience(alias));
		
		Message m = new Message();
		m.setMsg_content(message);
		m.setExtras(extras);
		pl.setMessage(m);

		return pl ;
	}
	
	//notification will show in phone message bar .
	public static List<Payload> createNotification(Map<String,NotificationSetting> notificationSettingMap , String message , Object extras)
	{
		Map<String,List<String>> notifictionMap =  new HashMap<>();
		for(String alia : notificationSettingMap.keySet()){
			NotificationSetting ns = notificationSettingMap.get(alia);
			if(ns == null){
				if(notifictionMap.containsKey("null")){
					notifictionMap.get("null").add(alia);
				}else{
					List<String> aliaList = new ArrayList<>();
					aliaList.add(alia);
					notifictionMap.put("null",aliaList);
				}
			}
			else{
				String sb = ns.getSound() + "_" + ns.getBuilder_id();
				if(notifictionMap.containsKey(sb)){
					notifictionMap.get(sb).add(alia);
				}else{
					List<String> aliaList = new ArrayList<>();
					aliaList.add(alia);
					notifictionMap.put(sb,aliaList);
				}
			}
		}
		List<Payload> plList = new ArrayList<>();
		for(String sb : notifictionMap.keySet()){
			List<String> aliaList = notifictionMap.get(sb);
			Payload pl = new Payload();
			pl.setAudience(creattAudience(aliaList.toArray(new String[aliaList.size()])));
			Notification n = new Notification();
			n.setAlert(message);
			n.getAndroid().setExtras(extras);
			n.getIos().setExtras(extras);
			n.getAndroid().setTitle("");
			if(!"null".equals(sb)){
				String[] sb_ = sb.split("_");
				String sound = sb_[0];
				try{
					int builder_id = 1;
					if (StringUtils.isNumeric(sb_[1])){
						builder_id = Integer.valueOf(sb_[1]);
					}
					n.getAndroid().setBuilder_id(builder_id);
				}catch (Exception e){
					e.printStackTrace();
				}
				n.getIos().setSound(sound);
			}
			pl.setNotification(n);
			plList.add(pl);
		}

		return plList ;
	}

	//notification will show in phone message bar .
	public static Payload createNotification(String[] alias , String message , Object extras)
	{
		Payload pl = new Payload();

		pl.setAudience(creattAudience(alias));

		Notification n = new Notification();
		n.setAlert(message);
		n.getAndroid().setExtras(extras);
		n.getIos().setExtras(extras);
		n.getAndroid().setTitle("");

		pl.setNotification(n);
		return pl ;
	}
	
	public static Extras createExtras(String type)
	{
		Extras e = new Extras();
		e.setType(type);
		
		return e;
	}
	
	private static Audience creattAudience(String[] alias)
	{
		Audience a = new Audience();
		a.setAlias(alias);
	
		return a ;
	}
}
