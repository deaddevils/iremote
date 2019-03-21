package com.iremote.common;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.OemProductorHelper;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.vo.merge.IMerge;

public class Utils {
	
	private static Log log = LogFactory.getLog(Utils.class);
	private static String[] DEVICE_DEFAULT_STATUSES_VALUES;
	private static int[] DEVICE_DEFAULT_STATUS;
	public static HashMap<String, Integer> DEVICE_TYPE_CHANNEL_MAP = new HashMap<>();
	public final static String AC_DEFAULT_STATUSES = "[25 ,1,2,1,1,2,1]" ; 
	public final static List<String> WAKEUP_GATEWAY_ID_PREFIX = Arrays.asList(new String[]{"iRemote30061","iRemote30062","iRemote30071"});

	static{
		setDeviceDefaultStatusesValues();
		setDeviceDefaultStatusValues();
		setDeviceTypeChannelMap();
	}

	private static void setDeviceTypeChannelMap() {
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH, 2);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH, 3);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_SCENE_PANNEL, 2);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_SCENE_PANNEL, 3);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_SCENE_PANNEL, 4);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_2_CHANNEL_SWITCH, 2);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_3_CHANNEL_SWITCH, 3);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_4_CHANNEL_SWITCH, 4);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_6_CHANNEL_SCENE_PANNEL, 6);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_SWITCH_SCENE_PANNEL, 2);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_SWITCH_SCENE_PANNEL, 3);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_SWITCH_SCENE_PANNEL, 4);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_DSC, 8);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_2_GAN_PORTABLE_SCENE_PANNEL, 2);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_3_GAN_PORTABLE_SCENE_PANNEL, 3);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_4_GAN_PORTABLE_SCENE_PANNEL, 4);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE, 2);
		DEVICE_TYPE_CHANNEL_MAP.put(IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_BINARY_SWITCH, 4);
	}

	private static void setDeviceDefaultStatusesValues() {
		DEVICE_DEFAULT_STATUSES_VALUES = new String[65];
		DEVICE_DEFAULT_STATUSES_VALUES[6] = "[-300,0,-1,0,-1]";
		DEVICE_DEFAULT_STATUSES_VALUES[8] = "[0,0]";
		DEVICE_DEFAULT_STATUSES_VALUES[9] = "[0,0,0]";
		DEVICE_DEFAULT_STATUSES_VALUES[14] = "[1,3,25,77]";
		DEVICE_DEFAULT_STATUSES_VALUES[29] = "[0]";
		DEVICE_DEFAULT_STATUSES_VALUES[32] = "[0,0]";
		DEVICE_DEFAULT_STATUSES_VALUES[33] = "[0,0,0]";
		DEVICE_DEFAULT_STATUSES_VALUES[34] = "[0,0,0,0]";
		DEVICE_DEFAULT_STATUSES_VALUES[36] = "[0,0,0,0,0,0,0,0,24]";
		DEVICE_DEFAULT_STATUSES_VALUES[40] = "[1,0,0]";
		DEVICE_DEFAULT_STATUSES_VALUES[41] = "[0]";
		DEVICE_DEFAULT_STATUSES_VALUES[42] = "[0,0]";
		DEVICE_DEFAULT_STATUSES_VALUES[43] = "[0,0,0]";
		DEVICE_DEFAULT_STATUSES_VALUES[46] = "[0,0,0,0,0,0,0,0,0,0,50]";
		DEVICE_DEFAULT_STATUSES_VALUES[51] = "[0,0]";
		DEVICE_DEFAULT_STATUSES_VALUES[56] = "[-1,-1,-1,-1,-1,-1,-1,-1,-1]";
		DEVICE_DEFAULT_STATUSES_VALUES[63] = "[-1,-1,-1,-1]";
		DEVICE_DEFAULT_STATUSES_VALUES[64] = "[0,0,0,0]";
	}

	private static void setDeviceDefaultStatusValues() {
		DEVICE_DEFAULT_STATUS = new int[36];
		DEVICE_DEFAULT_STATUS[5] = 255;
	}

	public static Date currentTimeAdd(int field, int amount)
	{
		Calendar c = Calendar.getInstance();
		c.add(field, amount);
		return c.getTime();
	}
	
	public static Date parseTime(String time)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null ;
		try {
			d = sf.parse(time);
		} catch (ParseException e) {
			log.error("" , e);
			return null;
		}
		return d ;
	}
	
	public static Date parseMin(String time , String defaultvalue)
	{
		if ( StringUtils.isNotBlank(time))
			return parseMin(time);
		else
			return parseMin(defaultvalue);
	}
	
	public static Date parseMin(String time)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = null ;
		try {
			d = sf.parse(time);
		} catch (ParseException e) {
			log.error("" , e);
			return null;
		}
		return d ;
	}
	
	public static Date parseDay(String time)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null ;
		try {
			d = sf.parse(time);
		} catch (ParseException e) {
			log.error("" , e);
			return null;
		}
		return d ;
	}
	
	public static Date parseTime(int second)
	{
		Calendar d = Calendar.getInstance();
		d.setTimeInMillis(second * 1000L);
		return d.getTime();
	}
	
	public static String formatTime(Date date)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}
	
	public static String formatTime(Date date , String format)
	{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}
	
	public static int readint(byte[] b , int beginindex , int endindex )
	{
		int value = 0 ;
		for ( int i = 0 ; i < b.length && beginindex + i  < endindex ; i ++ )
		{
			value *=256 ;
			value += b[beginindex + i] & 0xff ;
		}
		return value;
	}
	
	public static int readint(byte[] b )
	{
		return readint(b , 0 , b.length);
	}
	
	public static int readsignedint(byte[] b , int beginindex , int endindex )
	{
		int value = 0 ;
		for ( int i = 0 ; i < b.length && beginindex + i  < endindex ; i ++ )
		{
			if ( i == 0 )
				value = b[beginindex + i];
			else 
			{
				value = value << 8 ;
				value |= b[beginindex + i] & 0xff;
			}
		}
		return value;
	}

	public static MessageParser createWarningMessage(String type , int platform, String language  ,String devicename)
	{
		if ( devicename == null )
			devicename = "";
		MessageParser mp = MessageManager.getMessageParser(type, platform, language);
		if ( mp == null )
			return null ;
		mp.getParameter().put("name", devicename);
		return mp;
	}

	public static MessageParser createWarningMessage(String type , int platform, String language  ,String partitionname, String username)
	{
		if ( partitionname == null )
			partitionname = "";
		if (username == null)
			username = "";

		MessageParser mp = MessageManager.getMessageParser(type, platform, language);
		if ( mp == null )
			return null ;
		mp.getParameter().put("username", username);
		mp.getParameter().put("partitionname", partitionname);
		return mp;
	}
	
	public static String createtoken()
	{
		Random random = new Random((new Date()).getTime());
		int r = random.nextInt(999999);
		
		String strr = String.format("%06d" , r);
		
		UUID u = UUID.randomUUID();
		
		String us = u.toString().replaceAll("-", "");
		
		return us + strr;
	}
	
	private static StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"); 
	public static String createsecuritytoken()
	{
		StringBuffer sb = new StringBuffer();
		SecureRandom r = new SecureRandom();

		for ( int i = 0 ; i < 3 ; i ++ )
		{
			for ( int j = 0 ; j < 8 ; j ++ )
				sb.append(buffer.charAt(r.nextInt(buffer.length())));
		}
		return sb.toString();
	}
	
	public static String createsecuritytoken(int length)
	{
		StringBuffer sb = new StringBuffer();
		SecureRandom r = new SecureRandom();
		
		for ( int i = 0 ; i < length ; i ++ )
			sb.append(buffer.charAt(r.nextInt(buffer.length())));

		return sb.toString();
	}
	
	public static byte[] createsecuritykey(int length)
	{
		byte[] b = new byte[length];
		
		SecureRandom r = new SecureRandom();
		r.nextBytes(b);
		
		return b ;
	}
	
	public static String inttoString(int value)
	{
		if ( value <= 0 )
			return null ;
		StringBuffer sb = new StringBuffer();
		for ( ; value > 0 ; value = value /buffer.length())
			sb.append(buffer.charAt(value%buffer.length()));
		return sb.toString();
	}

	public static String createAlias(int id)
	{
		UUID u = UUID.randomUUID();
		String su = u.toString().replaceAll("-", "");
		
		Random r = new Random();
		int ri = r.nextInt(su.length());
		
		StringBuffer sb = new StringBuffer();
		sb.append(su.substring(0, ri)).append(String.valueOf(id)).append(su.substring(ri));
		
		return sb.toString();
	}

	public static String createZwaveDeviceid(String deviceid  , int dbid , int nuid)
	{
		if ( deviceid.endsWith("0000"))
			return String.format("%s_%03d", deviceid.substring(0, deviceid.length() - 4 ) , nuid);
		else 
			return String.format("%s_%d", deviceid , dbid);
	}
	
	public static int calculateTimeout(int heartbeat)
	{
		if ( heartbeat <= 30 )
			return ( heartbeat * 6 + 3 ) * 1000;
		else 
			return ( heartbeat * 3 + 3 ) * 1000;
	}
	
	public static byte[] wrapRequest(byte[] b) 
	{
		int c = 0 ;
		for ( int i = 0 ; i < b.length - 1 ; i ++ )
			c =  ( c ^ b[i]);
		b[b.length -1] = (byte)c;
		
		byte[] wb = new byte[b.length * 2 + 2];
		
		wb[0] = 0x7e;
		int i = 0 , wi = 1;
		for (  ; i < b.length ; i ++ , wi ++ )
		{
			if ( b[i] == 0x7e )
			{
				wb[wi++] = 0x7d;
				wb[wi] = 0x02;
			}
			else if ( b[i] == 0x7d )
			{
				wb[wi++] = 0x7d;
				wb[wi] = 0x01;
			}
			else 
				wb[wi] = b[i];
		}
		wb[wi] = 0x7e;
		byte[] rb = new byte[wi + 1];
		System.arraycopy(wb, 0, rb, 0, wi+1);
		
		return rb;
	}
	
	public static int getRemotePlatform(String deviceid)
	{
		return GatewayUtils.getRemotePlatform(deviceid);
	}
	
	public static boolean hasArmFunction(int platform)
	{
		return OemProductorHelper.hasArmFunction(platform);
	}
	
	
	public static String time2excutetime(int time){
		String excutetime = "";
		if(time > 0){
			String hour = time % 256 + ""; 
			if(hour.length() < 2)
				hour = "0" + hour;
			String minute = time / 256 + ""; 
			if(minute.length() < 2)
				minute = "0" + minute;
			excutetime = hour + ":" + minute;
		}
		return excutetime;
	}
	
	public static int excutetime2time(String excutetime){
		if ( excutetime == null || excutetime.length() != 5 )
			return 0 ;
		String[] t =excutetime.split(":");
		if ( t == null || t.length != 2 )
			return 0 ;
		return  (Integer.valueOf(t[1]) * 256 + Integer.valueOf(t[0]));
	}
	
	public static String getUserLanguage(int platform , String language)
	{
		String l = language;
		if ( platform == 0 || platform == 4 || platform == 3 || platform == 2 )
			l = language;
		if ( platform == 1 )
			return IRemoteConstantDefine.DEFAULT_LANGUAGE;

		if ( l == null || l.length() == 0 )
			return IRemoteConstantDefine.DEFAULT_LANGUAGE;
		if ( IRemoteConstantDefine.DEFAULT_LANGUAGE.equalsIgnoreCase(l) ||
			 IRemoteConstantDefine.DEFAULT_UNCH_LANGUAGE.equalsIgnoreCase(l) ||
			 IRemoteConstantDefine.DEFAULT_FRCA_LANGUAGE.equalsIgnoreCase(l) ||
			 IRemoteConstantDefine.DEFAULT_VIVN_LANGUAGE.equalsIgnoreCase(l) ||
			 IRemoteConstantDefine.DEFAULT_ZHHK_LANGUAGE.equalsIgnoreCase(l) ){
			return l;
		}

		return IRemoteConstantDefine.DEFAULT_UNCH_LANGUAGE;
	}
	
	public static boolean isLockTempPassordforSMSSend(int usercode)
	{
		return ( usercode >= 0xf3 && usercode <= 0xf3 + 10 );
	}
	
	public static boolean isTempPassword(int usercode)
	{
		return ( usercode == IRemoteConstantDefine.DOOR_LOCK_USER_CODE_TEMP_PASSWORD ); 
	}

	public static byte[] unwrapRequest(byte[] b)
	{
		byte[] ub = new byte[b.length];
		
		for ( int i = 0 , ui = 0 ; i < b.length ; i ++ , ui ++ )
		{
			if ( b[i] == 0x7d )
			{
				i++ ;
				if ( b[i] == 0x01 )
					ub[ui] = 0x7d;
				else if ( b[i] == 0x02 )
					ub[ui] = 0x7e ;
			}
			else 
				ub[ui] = b[i];
		}
		return ub;
	}
	
	
	public static byte[][] splitRequest(byte[] content, int length) 
	{
		List<byte[]> lst = new ArrayList<byte[]>();
		
		for ( int i = findRequestStart(content , 0 , length) ; i < length && i != -1 ; i = findRequestStart(content , i , length))
		{
			byte[] b = getRequestData(content , i , length);
			if ( b == null )
				break;
			b = unwrapRequest(b);
			lst.add(b);
			i += ( b.length + 2 ) ;
		}
		return lst.toArray(new byte[0][0]);
	}
	
	private static int findRequestStart(byte[] content , int index , int length)
	{
		for ( int i = index ; i < length -1 ; i ++ )
		{
			if ( content[i] == 0x7e && content[i+1] != 0x7e )
				return i ;
		}
		return -1 ;
	}
	
	private static byte[] getRequestData(byte[] content , int index , int length)
	{
		int e = findRequestEnd(content , index , length );
		if ( e == -1 )
			return null ;
		int l = e - index - 1;
		if ( l == 0 )
			return null ;
		byte[] b = new byte[l];
		System.arraycopy(content, index + 1 , b, 0, l);
		return b ;
	}
	
	private static int findRequestEnd(byte[] content , int index , int length)
	{
		for ( int i = index + 1 ; i < length ; i ++ )
		{
			if ( content[i] == 0x7e )
				return i ;
		}
		
		return -1 ;
	}
	
	public static void printMockData(String info , byte[] b )
	{
		if ( b == null )
			log.info("byte array is null");
		else if ( log.isInfoEnabled() ) 
		{
			StringBuffer sb = new StringBuffer(info);
			sb.append(" ");
			sb.append("126 ");
			for ( int i = 0 ; i < b.length && i < b.length ; i ++ )
				sb.append(b[i] & 0xff ).append(" ");
			sb.append("126 ");
			log.info(sb.toString());
		}
	}
	
	public static void print(String info , byte[] b )
	{
		if ( b == null )
			b = new byte[0];
		print(info , b , b.length);
	}
	
	public static void print(String info , byte[] b , int length)
	{
		print(log , info , b , length);
	}
	
	public static void print(Log logger , String info , byte[] b , int length)
	{
		if ( b == null )
			logger.info("byte array is null");
		else if ( logger.isInfoEnabled() ) 
		{
			StringBuffer sb = new StringBuffer(info);
			sb.append(" ");
			for ( int i = 0 ; i < b.length && i < length ; i ++ )
				sb.append(b[i] & 0xff ).append(" ");
			logger.info(sb.toString());
		}
	}
	
	public static void printInfo(Logger logger , String info , byte[] b , int length)
	{
		if ( b == null )
			logger.info("byte array is null");
		else if ( logger.isInfoEnabled() ) 
		{
			StringBuffer sb = new StringBuffer(info);
			sb.append(" ");
			for ( int i = 0 ; i < b.length && i < length ; i ++ )
				sb.append(b[i] & 0xff ).append(" ");
			logger.info(sb.toString());
		}
	}
	
	public static String byteArraytoString(byte[] b)
	{
		StringBuffer sb = new StringBuffer("[");
		String prefix = "";
		if ( b != null && b.length > 0 )
		{
			for ( int i = 0 ; i < b.length ; i ++ )
			{
				sb.append(prefix).append(b[i] & 0xff);
				prefix = ",";
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static String makekey(int key1 , int key2 )
	{
		return makekey( String.valueOf(key1) , String.valueOf(key2));
	}
	
	public static String makekey(String key1 , String key2 )
	{
		return String.format("%s_%s", key1 , key2);
	}
	
	public static String makekey(String key1 , int key2 , int key3 , int key4)
	{
		return String.format("%s_%d_%d_%d", key1 , key2 , key3 , key4);
	}
	
	public static int compareString(String s1 , String s2 )
	{
		if ( s1 == null && s2 == null )
			return 0 ;
		if ( s1 == null )
			return -1 ;
		if ( s2 == null )
			return 1 ;
		return s1.compareTo(s2);
	}
	
	public static float celsius2fahrenheit(float t)
	{
		return (float)(( t * 1.8 ) + 32) ;
	}
	
	public static float fahrenheit2celsius(float t)
	{
		return (float)(( t - 32) / 1.8 )  ;
	}
	
	public static int time2second(String time)
	{
		if ( time == null || time.length() != 5 )
			return 0 ;
		String[] t =time.split(":");
		if ( t == null || t.length != 2 )
			return 0 ;
		return (Integer.valueOf(t[0]) * 60 + Integer.valueOf(t[1]))*60;
	}
	
	public static String getGatewayDefaultName(String deviceid)
	{
		if ( StringUtils.isBlank(deviceid))
			return "" ;
		if ( deviceid.length() <= 6 )
			return deviceid ;
		
		return deviceid.substring(deviceid.length() - 6);
	}
	
	public static String getDeviceDefaultStatuses(String devicetype)
	{
		if ( devicetype == null )
			return DEVICE_DEFAULT_STATUSES_VALUES[0];
		int dt = Integer.valueOf(devicetype);
		return getDeviceDefaultStatuses(dt);
	}
	
	public static String getDeviceDefaultStatuses(int devicetype)
	{
		if( devicetype >= DEVICE_DEFAULT_STATUSES_VALUES.length)
			return DEVICE_DEFAULT_STATUSES_VALUES[0] ;
		return DEVICE_DEFAULT_STATUSES_VALUES[devicetype];
	}
	
	public static int getDeviceDefaultStatus(String devicetype)
	{
		if ( devicetype == null )
			return DEVICE_DEFAULT_STATUS[0];
		int dt = Integer.valueOf(devicetype);
		return getDeviceDefaultStatus(dt);
	}
	
	public static int getDeviceDefaultStatus(int devicetype)
	{
		if( devicetype >= DEVICE_DEFAULT_STATUS.length)
			return DEVICE_DEFAULT_STATUS[0] ;
		return DEVICE_DEFAULT_STATUS[devicetype];
	}
	
	public static  String unalarmmessage(String devicetype , int warningstatus)
	{
		String key = String.format("%s_%d",devicetype , warningstatus);
		
		String m = IRemoteConstantDefine.DEVICE_WARNING_MESSAGE_MAP.get(key);
		if ( m == null )
			return null ;
		return "unalarm"+m;
	}
	
	public static <T> List<T> merge(Collection<T> desc , Collection<T> src , Comparator<T> comparator , IMerge<T>  merge)
	{
		List<T> rl = new ArrayList<T>();
		if ( desc == null )
			desc = new ArrayList<T>();
		if ( src == null )
			src = new ArrayList<T>();
		
		for ( T td : desc )
		{
			T tf = find(src , td , comparator);
			if ( tf == null )
				rl.add(td);
			else 
				merge.merge(td, tf);
		}
		
		desc.removeAll(rl);
		
		for ( T ts : src )
		{
			T tf = find(desc , ts , comparator );
			if ( tf == null )
				desc.add(ts);
		}

		return rl ;
		
	}
	
	private static <T> T find(Collection<T> collect , T t , Comparator<T> comparator )
	{
		for ( T tc : collect )
			if ( comparator.compare(tc, t ) == 0 )
				return tc;
		return null;
	}
	
	public static void sleep(long millis)
	{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static boolean isJsonArrayContaints(String jsonarray , int value)
	{
		if ( jsonarray == null ||  jsonarray.length() == 0 )
			return false ;
		JSONArray ja = JSON.parseArray(jsonarray);
		return ja.contains(value);
	}
	
	public static String jsonArrayRemove(String jsonarray , int value)
	{
		if ( jsonarray == null || jsonarray.length() == 0 )
			jsonarray = "[]";
		JSONArray ja = JSON.parseArray(jsonarray);
		for ( int i = 0 ; i < ja.size() ; i ++ )
			if ( ja.getInteger(i) == value )
			{
				ja.remove(i);
				return ja.toJSONString();
			}
				
		return ja.toJSONString();
	}
	
	public static String jsonArrayAppend(String jsonarray , int value)
	{
		if ( jsonarray == null || jsonarray.length() == 0 )
			jsonarray = "[]";
		JSONArray ja = JSON.parseArray(jsonarray);
		ja.add(value);
		return ja.toJSONString();
	}

	public static JSONArray appendValueIntoJSONArray(String jsonArray, int value, int size) {
		if ( jsonArray == null || jsonArray.length() == 0 )
			jsonArray = "[]";
		JSONArray json = JSON.parseArray(jsonArray);
		if (json.size() >= size) {
			json.set(size - 1, value);
			return json;
		}
		while (json.size() < size - 1) {
			json.add(0);
		}
		json.add(value);
		return json;
	}
	
	public static Integer getJsonArrayValue(String jsonarray , int index)
	{
		if ( jsonarray == null || jsonarray.length() == 0 )
			return null ;
		JSONArray ja = JSON.parseArray(jsonarray);
		if ( index >= ja.size() )
			return null ;
		return ja.getInteger(index);
	}
	
	public static Integer getJsonArrayValue(String jsonarray , int index , int defaultvalue)
	{
		Integer v = getJsonArrayValue(jsonarray , index);
		if ( v == null )
			return defaultvalue ;
		return v ;
	}
	
	public static Integer getJsonArrayLastValue(String jsonarray) {
		if ( jsonarray == null || jsonarray.length() == 0 )
			return null ;
		JSONArray ja = JSON.parseArray(jsonarray);
		if ( ja.size() == 0 )
			return null ;
		return ja.getInteger(ja.size() -1);
	}
	
	public static String setJsonArrayValue(String jsonarray, int index , float value)
	{
		if ( StringUtils.isBlank(jsonarray))
			jsonarray = "[]";
		JSONArray ja = JSON.parseArray(jsonarray);
		ja.set(index,Float.valueOf(String.format("%.3f", value)));
		return ja.toJSONString();
	}
	
	public static String setJsonArrayValue(String jsonarray, int index , int value)
	{
		if ( StringUtils.isBlank(jsonarray))
			jsonarray = "[]";
		JSONArray ja = JSON.parseArray(jsonarray);
		ja.set(index, value);
		return ja.toJSONString();
	}
	
	public static String setJsonArrayValue(String jsonarray, int index , String value)
	{
		if ( StringUtils.isBlank(jsonarray))
			jsonarray = "[]";
		JSONArray ja = JSON.parseArray(jsonarray);
		ja.set(index, value);
		return ja.toJSONString();
	}
	
	public static String getJsonStringValue(JSONObject json , String key , String defaultvalue)
	{
		if ( json.containsKey(key) )
		{
			String v = json.getString(key);
			if ( v == null || v.length() == 0 )
				return defaultvalue ;
			return v ;
		}
		return defaultvalue ;
	}
	
	public static int[] jsontoIntArray(String ary)
	{
		if ( ary == null || ary.length() == 0 )
			ary = "[]";
		JSONArray ja = JSON.parseArray(ary);
		Integer[] ia = ja.toArray(new Integer[0]);
		
		int[] ita = new int[ia.length];
		for ( int i = 0 ; i < ia.length; i ++ )
			ita[i] = ia[i] ;
		return ita;
	}
	
	public static List<Integer> jsontoIntList(String ary)
	{
		int[] ia = jsontoIntArray(ary);
		if ( ia == null || ia.length == 0 )
			return new ArrayList<Integer>();
		List<Integer> lst = new ArrayList<Integer>();
		for ( int i = 0 ; i < ia.length ; i ++ )
			lst.add(ia[i]);
		return lst ;
	}
	
	public static boolean isByteMatch(Byte[] key , byte[] cmd )
	{
		if ( key.length > cmd.length )
			return false ;
		for ( int i = 0 ; i < key.length ; i ++ )
			if ( key[i] != null && key[i] != cmd[i] )
				return false ;
		return true;
	}
	
	public static boolean isByteMatch(Byte[] key , Byte[] cmd )
	{
		if ( key.length > cmd.length )
			return false ;
		for ( int i = 0 ; i < key.length ; i ++ )
			if ( key[i] != null && key[i] != cmd[i] )
				return false ;
		return true;
	}

	public static Integer getDeviceChannelCount(String deviceType){
		return DEVICE_TYPE_CHANNEL_MAP.containsKey(deviceType) ? DEVICE_TYPE_CHANNEL_MAP.get(deviceType) : 0;
	}

	public static String getDeviceChannelDefaultStatuses(String deviceType) {
		HashMap<String, String> channelStatuses = new HashMap<>();
		channelStatuses.put(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE, "[-1,-1,-1,-1,-1,-1,-1,-1,-1]");
		return channelStatuses.containsKey(deviceType) ? channelStatuses.get(deviceType) : null;
	}
	
	public static void main(String arg[])
	{
		System.out.println(Utils.readsignedint(new byte[]{0,0,0,1}, 0, 4));
		System.out.println(Utils.readsignedint(new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff}, 0, 4));
		System.out.println(Utils.readsignedint(new byte[]{(byte)0x8F,(byte)0x0f}, 0, 2));
		
//		System.out.println(Utils.formatTime(Utils.parseTime(1480750948)));
//		System.out.println(Utils.isJsonArrayContaints("[]", 255));
//		System.out.println(Utils.isJsonArrayContaints("[251,255]", 255));
//		System.out.println(Utils.jsonArrayAppend("[251]", 255));
	}
}
