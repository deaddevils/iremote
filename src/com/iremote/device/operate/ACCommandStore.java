package com.iremote.device.operate;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.Utils;
import com.iremote.common.processorstore.ClassMapper;
import com.iremote.device.operate.infrareddevice.ac.ACOperateCommandBase;

public class ACCommandStore extends ClassMapper<ACOperateCommandBase>
{
	private static Log log = LogFactory.getLog(ACCommandStore.class);
	private static ACCommandStore instance = new ACCommandStore();
	
	public static ACCommandStore getInstance()
	{
		return instance ;
	}
	
	public ACOperateCommandBase getProcessor(JSONObject json)
	{
		return super.getProcessor(makeKey(json));
	}
	
	public ACCommandStore()
	{
		try
		{
			InputStream io = this.getClass().getClassLoader().getResourceAsStream("resource/gowildaccommand.properties");
			byte[] bc = new byte[io.available()];
			io.read(bc);
			String sc = new String(bc);
			JSONArray ja = JSON.parseArray(sc);
			
			for ( int i = 0 ; i < ja.size() ; i ++ )
			{
				JSONObject json = ja.getJSONObject(i);
				
				Class<? extends ACOperateCommandBase> cls = (Class<? extends ACOperateCommandBase>) Class.forName(json.getString("class"));
				super.registProcessor(makeKey(json), cls);
			}
		}
		catch(Throwable t )
		{
			log.error(t.getMessage() , t);
		}
	}
	
	private static String makeKey(JSONObject json)
	{
		return String.format("%s_%s_%s_%s_%s",Utils.getJsonStringValue(json, "state", ""),
													Utils.getJsonStringValue(json, "action", ""),
													Utils.getJsonStringValue(json, "attribute", ""),
													Utils.getJsonStringValue(json, "attributeValue", ""),
													Utils.getJsonStringValue(json, "mode", ""));
	}
}
