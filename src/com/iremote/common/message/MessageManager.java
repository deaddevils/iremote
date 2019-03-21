package com.iremote.common.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.domain.MessageTemplate;
import com.iremote.service.MessageTemplateService;
import org.xsocket.connection.Server;

public class MessageManager {
	
	public static final Map<String , String> MESSAGE_TEMPLATE_MAP = new HashMap<String , String>();
	
	public static void init()
	{
		MessageTemplateService svr = new MessageTemplateService();
		List<MessageTemplate> lst = svr.query();
		
		for ( MessageTemplate mt : lst )
			MESSAGE_TEMPLATE_MAP.put(makekey(mt.getKey() ,mt.getPlatform(), mt.getLanguage()), mt.getValue());
	}
	
	public static MessageParser getMessageParser(String key , int platform , String language) 
	{
		MessageTemplateService svr = new MessageTemplateService(); 
		MessageTemplate mt = svr.queryPlatformorDefaultTempalte(key, platform, language);

		if ( mt == null )
		{
			String defaultlanguage = ServerRuntime.getInstance().getDefaultlanguage();
			if (defaultlanguage.equalsIgnoreCase(language))
				return null;
			else
				mt = svr.queryPlatformorDefaultTempalte(key, platform, defaultlanguage);
		}

		if (mt == null)
			return null;

		JSONObject json = null;
		if ( mt.getAlitemplateparam() != null )
			json = JSON.parseObject(mt.getAlitemplateparam());
		
		return new MessageParser(mt.getValue() , mt.getAlitemplatecode() , json );
	}

	public static MessageParser getMessageParser(String key , int platform , String language,JSONObject json)
	{
		MessageTemplateService svr = new MessageTemplateService();
		MessageTemplate mt = svr.queryPlatformorDefaultTempalte(key, platform, language);

		if ( mt == null )
		{
			String defaultlanguage = ServerRuntime.getInstance().getDefaultlanguage();
			if (defaultlanguage.equalsIgnoreCase(language))
				return null;
			else
				mt = svr.queryPlatformorDefaultTempalte(key, platform, defaultlanguage);
		}

		if ( mt == null )
			return null ;

		return new MessageParser(mt.getValue() , mt.getAlitemplatecode() , json );
	}

	public static String getmessage(String key , int platform , String language)
	{
		if ( language == null )
			language = IRemoteConstantDefine.DEFAULT_LANGUAGE;
		MessageParser mp = getMessageParser(key , platform , language);
		if ( mp == null )
			return null; 
		return mp.getMessage();
	}
		
	private static String makekey(String key , int platform , String language)
	{
		if ( language == null )
			language = IRemoteConstantDefine.DEFAULT_LANGUAGE;
		return String.format("%s_%d_%s", key , platform , language.toLowerCase());
	}
}
