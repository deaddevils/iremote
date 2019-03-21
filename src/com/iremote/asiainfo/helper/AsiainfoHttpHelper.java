package com.iremote.asiainfo.helper;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.asiainfo.AnyTrustStrategy;
import com.iremote.asiainfo.connection.WebSocketClientConnection;
import com.iremote.asiainfo.connection.WebSocketWrap;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.asiainfo.vo.BusinessData;
import com.iremote.asiainfo.vo.CommonResponse;
import com.iremote.asiainfo.vo.DeviceBindingInfo;
import com.iremote.asiainfo.vo.DeviceCategory;
import com.iremote.asiainfo.vo.DeviceInfo;
import com.iremote.asiainfo.vo.StatusTranslate;
import com.iremote.asiainfo.vo.UnregistDevice;
import com.iremote.asiainfo.vo.UserInfo;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandParser;
import com.iremote.common.commandclass.CommandValue;
import com.iremote.common.thread.KeyCrashException;
import com.iremote.common.thread.SynchronizeRequestManager;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;

public class AsiainfoHttpHelper {

	private static Log log = LogFactory.getLog(AsiainfoHttpHelper.class);
	private static int sequence = 0 ;

	private static Map<String , DeviceCategory> DeviceCategoryMap = new HashMap<String , DeviceCategory>();
	
	private static Map<String ,StatusTranslate> ReportTranslateMap = new HashMap<String , StatusTranslate>();
	
	static
	{
		DeviceCategoryMap.put("infrared_AC", new DeviceCategory("JWZH-O-2015-0-3","H003","H001003"));
		DeviceCategoryMap.put("infrared_TV", new DeviceCategory("JWZH-O-2015-0-2","H002","H002001"));
		DeviceCategoryMap.put("infrared_STB", new DeviceCategory("JWZH-O-2015-0-1","H001","H001001"));
		
		DeviceCategoryMap.put("zWave_1", new DeviceCategory(IRemoteConstantDefine.MODLE[1],IRemoteConstantDefine.DEVICE_CATEGORIES[1],IRemoteConstantDefine.DEVICE_CLASS[1]));
		DeviceCategoryMap.put("zWave_2", new DeviceCategory(IRemoteConstantDefine.MODLE[2],IRemoteConstantDefine.DEVICE_CATEGORIES[2],IRemoteConstantDefine.DEVICE_CLASS[2]));
		DeviceCategoryMap.put("zWave_3", new DeviceCategory(IRemoteConstantDefine.MODLE[3],IRemoteConstantDefine.DEVICE_CATEGORIES[3],IRemoteConstantDefine.DEVICE_CLASS[3]));
		DeviceCategoryMap.put("zWave_4", new DeviceCategory(IRemoteConstantDefine.MODLE[4],IRemoteConstantDefine.DEVICE_CATEGORIES[4],IRemoteConstantDefine.DEVICE_CLASS[4]));
		DeviceCategoryMap.put("zWave_5", new DeviceCategory(IRemoteConstantDefine.MODLE[5],IRemoteConstantDefine.DEVICE_CATEGORIES[5],IRemoteConstantDefine.DEVICE_CLASS[5]));
		DeviceCategoryMap.put("zWave_6", new DeviceCategory(IRemoteConstantDefine.MODLE[6],IRemoteConstantDefine.DEVICE_CATEGORIES[6],IRemoteConstantDefine.DEVICE_CLASS[6]));
		DeviceCategoryMap.put("zWave_7", new DeviceCategory(IRemoteConstantDefine.MODLE[7],IRemoteConstantDefine.DEVICE_CATEGORIES[7],IRemoteConstantDefine.DEVICE_CLASS[7]));
		DeviceCategoryMap.put("zWave_8", new DeviceCategory(IRemoteConstantDefine.MODLE[8],IRemoteConstantDefine.DEVICE_CATEGORIES[8],IRemoteConstantDefine.DEVICE_CLASS[8]));
		DeviceCategoryMap.put("zWave_9", new DeviceCategory(IRemoteConstantDefine.MODLE[9],IRemoteConstantDefine.DEVICE_CATEGORIES[9],IRemoteConstantDefine.DEVICE_CLASS[9]));
		DeviceCategoryMap.put("zWave_10", new DeviceCategory(IRemoteConstantDefine.MODLE[10],IRemoteConstantDefine.DEVICE_CATEGORIES[10],IRemoteConstantDefine.DEVICE_CLASS[10]));
		DeviceCategoryMap.put("zWave_11", new DeviceCategory(IRemoteConstantDefine.MODLE[11],IRemoteConstantDefine.DEVICE_CATEGORIES[11],IRemoteConstantDefine.DEVICE_CLASS[11]));
		
		
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_SMOKE, "255"), new StatusTranslate("setAlarm" , "alarm", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_SMOKE, "0"), new StatusTranslate("setAlarm" , "alarm", 0));
		
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_WATER, "255"), new StatusTranslate("setAlarm" , "alarm", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_WATER, "0"), new StatusTranslate("setAlarm" , "alarm", 0));
		
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_GAS, "255"), new StatusTranslate("setAlarm" , "alarm", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_GAS, "0"), new StatusTranslate("setAlarm" , "alarm", 0));
		
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR, "255"), new StatusTranslate("" , "doorState", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR, "0"), new StatusTranslate("" ,"doorState", 0));
		
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_MOVE, "255"), new StatusTranslate("" , "movingAlarm", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_MOVE, "0"), new StatusTranslate("" , "movingAlarm", 0));

		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, "255"), new StatusTranslate("setLock" ,"lock", 0));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, "1"), new StatusTranslate("setLock" ,"lock", 1));

		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, "255"), new StatusTranslate("setLock" ,"lock", 0));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, "1"), new StatusTranslate("setLock" ,"lock", 1));

		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_OUTLET, "255"), new StatusTranslate("setConnection" ,"connection", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_OUTLET, "0"), new StatusTranslate("setConnection" ,"connection", 0));

		
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_SMOKE, 0x9C ,0x02,0xff ), new StatusTranslate("setAlarm" , "alarm", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_SMOKE, 0x9C ,0x02,0x0 ), new StatusTranslate("setAlarm" , "alarm", 0));
		
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_WATER, 0x9C ,0x02,0xff), new StatusTranslate("setAlarm" , "alarm", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_WATER, 0x9C ,0x02,0x0), new StatusTranslate("setAlarm" , "alarm", 0));
		
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_GAS, 0x9C ,0x02,0xff), new StatusTranslate("setAlarm" , "alarm", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_GAS, 0x9C ,0x02,0x0), new StatusTranslate("setAlarm" , "alarm", 0));
	
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, 0x62 , 0x01, 255), new StatusTranslate("setLock" ,"lock", 0));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, 0x62 , 0x01, 1), new StatusTranslate("setLock" ,"lock", 1));

		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, 0x62 , 0x01, 255), new StatusTranslate("setLock" ,"lock", 0));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, 0x62 , 0x01, 1), new StatusTranslate("setLock" ,"lock", 1));

		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_OUTLET, 0x25 ,0x01 ,0xff), new StatusTranslate("setConnection" ,"connection", 1));
		ReportTranslateMap.put(Utils.makekey(IRemoteConstantDefine.DEVICE_TYPE_OUTLET, 0x25 ,0x01 ,0x00), new StatusTranslate("setConnection" ,"connection", 0));

	}
	
	
	public static StatusTranslate getReportTranslate(String devicetype , int value)
	{
		return getReportTranslate(devicetype , String.valueOf(value));
	}
	
	public static StatusTranslate getReportTranslate(String devicetype , String value)
	{
		return ReportTranslateMap.get(Utils.makekey(devicetype , value)) ;
	}
	
	public static boolean asiainfoUserAuth(String usertoken)
	{
		BusinessData data = new BusinessData();
		data.setMsgSeqNo(AsiainfoMessageHelper.createMessageSequence());
		data.setSystemId(null);
		data.setToken(usertoken);

		String str = httprequest(IRemoteConstantDefine.ASIA_INFO_APP_SERVER_URL  + "openapi/isValid",data);
		
		JSONObject json = JSON.parseObject(str);
		
		return ( json.containsKey("resultCode") && json.getIntValue("resultCode") == 0 );
	}
	
	public static JSONObject asiainfoUserinfo(String usertoken)
	{
		BusinessData data = new BusinessData();
		data.setMsgSeqNo(AsiainfoMessageHelper.createMessageSequence());
		data.setSystemId(null);
		data.setToken(usertoken);

		String str = httprequest(IRemoteConstantDefine.ASIA_INFO_APP_SERVER_URL  + "/openapi/getUserInfo",data);
		
		return JSON.parseObject(str);
	}
	
	public static void userinfo(PhoneUser user)
	{
		BusinessData data = new BusinessData();
		data.setSystemId(IRemoteConstantDefine.CLIENT_CODE_OF_SYSTEM_ID);
		data.setAccessToken(WebSocketClientConnection.getAccessToken());
		data.setMsgSeqNo(AsiainfoMessageHelper.createMessageSequence());
	
		UserInfo ui = new UserInfo();
		ui.setOpenId(String.valueOf(user.getPhoneuserid()));
		ui.setRegTime(Utils.formatTime(user.getCreatetime() , "yyyyMMddHHmmss"));
		ui.setChineseName(user.getPhonenumber());
		ui.setMobile(user.getPhonenumber());
		data.setMessage(ui);
		
		httprequest(IRemoteConstantDefine.ASIA_INFO_BASE_URL + "/partner/userInfoReg" ,data);
	}
	
	public static String deviceBinding(PhoneUser user , Remote r)
	{
		if ( user == null || r == null )
			return ""; 
		if ( r.getPlatform() !=  IRemoteConstantDefine.PLATFORM_ASININFO )
			return "";
		BusinessData data = new BusinessData();
		data.setSystemId(IRemoteConstantDefine.CLIENT_CODE_OF_SYSTEM_ID);
		data.setAccessToken(WebSocketClientConnection.getAccessToken());
		data.setMsgSeqNo(AsiainfoMessageHelper.createMessageSequence());
	
		DeviceBindingInfo db = new DeviceBindingInfo();
		db.setDeviceId(r.getDeviceid());
		db.setMainUserId(PhoneUserHelper.getUserId(user));
		db.setMainUserType(PhoneUserHelper.getUserType(user));
		db.setType("00");
		
		data.setMessage(db);
		
		return httprequest(IRemoteConstantDefine.ASIA_INFO_BASE_URL + "/partner/userDeviceRel" ,data);
	}
	
	public static void deviceBinding(PhoneUser user , ZWaveDevice device)
	{
		String did = Utils.createZwaveDeviceid(device.getDeviceid() , device.getZwavedeviceid(),device.getNuid());
		deviceBinding(user , did);
	}
	
	public static void deviceBinding(PhoneUser user , InfraredDevice device)
	{
		String did = device.getApplianceid();
		deviceBinding(user , did);
	}
	
	public static void deviceBinding(PhoneUser user , String did)
	{
		if ( user == null )
			return; 
		if ( user.getPlatform() != IRemoteConstantDefine.PLATFORM_ASININFO )
			return ;
		BusinessData data = new BusinessData();
		data.setSystemId(IRemoteConstantDefine.CLIENT_CODE_OF_SYSTEM_ID);
		data.setAccessToken(WebSocketClientConnection.getAccessToken());
		data.setMsgSeqNo(AsiainfoMessageHelper.createMessageSequence());
	
		DeviceBindingInfo db = new DeviceBindingInfo();
		db.setDeviceId(did);
		db.setMainUserId( PhoneUserHelper.getUserId(user));
		db.setMainUserType(PhoneUserHelper.getUserType(user));
		db.setType("00");
		
		data.setMessage(db);
		
		httprequest(IRemoteConstantDefine.ASIA_INFO_BASE_URL + "/partner/userDeviceRel" ,data);
	}
	
	public static void deviceUnbinding(PhoneUser user , Remote r)
	{
		if ( user == null || r == null )
			return; 
		if ( r.getPlatform() !=  IRemoteConstantDefine.PLATFORM_ASININFO)
			return;
		BusinessData data = new BusinessData();
		data.setSystemId(IRemoteConstantDefine.CLIENT_CODE_OF_SYSTEM_ID);
		data.setAccessToken(WebSocketClientConnection.getAccessToken());
		data.setMsgSeqNo(AsiainfoMessageHelper.createMessageSequence());
	
		DeviceBindingInfo db = new DeviceBindingInfo();
		db.setDeviceId(r.getDeviceid());
		db.setMainUserId(PhoneUserHelper.getUserId(user));
		db.setMainUserType(PhoneUserHelper.getUserType(user));
		db.setType("01");
		
		data.setMessage(db);
		
		httprequest(IRemoteConstantDefine.ASIA_INFO_BASE_URL + "/partner/userDeviceRel" ,data);
	}
	
	public static void deviceUnbinding(PhoneUser user , ZWaveDevice device)
	{
		String did = Utils.createZwaveDeviceid(device.getDeviceid() , device.getZwavedeviceid(),device.getNuid());
		deviceUnbinding(user , did);
	}
	
	public static void deviceUnbinding(PhoneUser user , InfraredDevice device)
	{
		String did = device.getApplianceid();
		deviceUnbinding(user , did);
	}
	
	public static void deviceUnbinding(PhoneUser user , String did )
	{
		if ( user == null )
			return; 
		if ( user.getPlatform() !=  IRemoteConstantDefine.PLATFORM_ASININFO)
			return;
		BusinessData data = new BusinessData();
		data.setSystemId(IRemoteConstantDefine.CLIENT_CODE_OF_SYSTEM_ID);
		data.setAccessToken(WebSocketClientConnection.getAccessToken());
		data.setMsgSeqNo(AsiainfoMessageHelper.createMessageSequence());
	
		DeviceBindingInfo db = new DeviceBindingInfo();
		db.setDeviceId(did);
		db.setMainUserId(PhoneUserHelper.getUserId(user));
		db.setMainUserType(PhoneUserHelper.getUserType(user));
		db.setType("01");
		
		data.setMessage(db);
		
		httprequest(IRemoteConstantDefine.ASIA_INFO_BASE_URL + "/partner/userDeviceRel" ,data);
	}
	
	public static void reportDeviceinfo(ZWaveDevice device )
	{
		String did = Utils.createZwaveDeviceid(device.getDeviceid() , device.getZwavedeviceid() , device.getNuid());
		
		reportDeviceinfo(device.getDeviceid() , device.getName(), did , DeviceCategoryMap.get(String.format("zWave_%s", device.getDevicetype())));
	}
	
	public static void reportDeviceinfo(InfraredDevice device )
	{
		String did = device.getApplianceid();
		
		reportDeviceinfo(device.getDeviceid() , device.getName(), did , DeviceCategoryMap.get(String.format("infrared_%s", device.getDevicetype())));
	}
	
	public static void unregistDeviceinfo(ZWaveDevice device )
	{
		String did = Utils.createZwaveDeviceid(device.getDeviceid() , device.getZwavedeviceid() , device.getNuid());
		unregistDevice(device.getDeviceid() , did);
	}
	
	public static void unregistDeviceinfo(InfraredDevice device )
	{
		String did = device.getApplianceid();
		
		unregistDevice(device.getDeviceid() , did);
	}
	
	public static void unregistDevice(String deviceid , String did)
	{
		UnregistDevice ud = new UnregistDevice(did ,Utils.formatTime(new Date() , "yyyyMMddHHmmss"));

		AsiainfoMessage message = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_UNREGIST_DEVICE,deviceid);
		message.setMessageinfo(JSON.toJSONString(ud));
		
		try {
			//SynchronizeRequestManager.regist(message.getSequence(), "Asininfo_request");
			WebSocketWrap.writeMessage(message);
			//CommonResponse response = (CommonResponse) SynchronizeRequestManager.getResponse(message.getSequence(), "Asininfo_request");
		} catch (TimeoutException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} 
	}
	
	public static void CommonResponse(AsiainfoMessage message)
	{
		AsiainfoMessage rm = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_COMMON_RESPONSE);
		CommonResponse cr = createResponse(message , 0 );
		rm.setMessageinfo(JSON.toJSONString(cr));
		
		WebSocketWrap.asynWriteMessage(rm);
	}
	
	private static CommonResponse createResponse(AsiainfoMessage message , int result)
	{
		CommonResponse r = new CommonResponse();
		r.setReplyMsgId(message.getMessageid());
		r.setReplySeriNum(message.getSequence());
		r.setReplyResult(result);
		return r;
	}
	
	public static void reportDeviceinfo(String deviceid , String deviceName , String did , DeviceCategory type )
	{
		if ( type == null )
			return ;

		DeviceInfo di = new DeviceInfo();
		di.setDeviceId(did);
		di.setManufacturer("∂‡¡È");
		di.setVersion("01");
		di.setBrand("∂‡¡È");
		di.setModel(type.getModel());
		di.setDeviceCategories(type.getDeviceCategories());
		di.setDeviceClass(type.getDeviceClass());
		di.setCreateTime(Utils.formatTime(new Date() , "yyyyMMddHHmmss"));
		di.setDeviceName(deviceName);
		
		AsiainfoMessage message = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_DEVICE_INFO_FILING,deviceid);
		message.setMessageinfo(JSON.toJSONString(di));
		
		try {
			SynchronizeRequestManager.regist(message.getSequence(), "Asininfo_request");
			WebSocketWrap.writeMessage(message);
			CommonResponse response = (CommonResponse) SynchronizeRequestManager.getResponse(message.getSequence(), "Asininfo_request");
		} catch (TimeoutException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (KeyCrashException e) {
			log.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static String httprequest(String url ,BusinessData data)
	{
		HttpClient httpclient = createHttpClient(url);
		try 
		{
			if ( log.isInfoEnabled() )
				log.info(JSON.toJSONString(data));
			
			HttpPost httpost = new HttpPost(url);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			
			if ( data.getMsgSeqNo() != null )
				nvps.add(new BasicNameValuePair("msgSeqNo", data.getMsgSeqNo()));
			if ( data.getSystemId() != null )
				nvps.add(new BasicNameValuePair("systemId", data.getSystemId()));
			if ( data.getMessage() != null )
			{
				String message = JSON.toJSONString(data.getMessage());			
				nvps.add(new BasicNameValuePair("message", message));
			}
			if ( data.getAccessToken() != null )
				nvps.add(new BasicNameValuePair("accessToken", data.getAccessToken()));
			if ( data.getCount() != 0 )
				nvps.add(new BasicNameValuePair("count", String.valueOf(data.getCount())));
			if ( data.getToken() != null )
				nvps.add(new BasicNameValuePair("userToken", data.getToken()));
						
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
						
			HttpEntity entity = response.getEntity();

			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
			
			return rst;
			
		} 
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		finally 
		{
			
		}
		return "";
	}
	
	public static HttpClient createHttpClient(String url)
	{
		if ( url.startsWith("https"))
		{
			RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();  
			ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();  
			registryBuilder.register("http", plainSF);  
			try {  
			    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
			    SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();  
			    LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
			    registryBuilder.register("https", sslSF);  
			} catch (KeyStoreException e) {  
			    throw new RuntimeException(e);  
			} catch (KeyManagementException e) {  
			    throw new RuntimeException(e);  
			} catch (NoSuchAlgorithmException e) {  
			    throw new RuntimeException(e);  
			}  
			Registry<ConnectionSocketFactory> registry = registryBuilder.build();  
			
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);  

			return HttpClientBuilder.create().setConnectionManager(connManager).build();
		}
		else 
			return HttpClientBuilder.create().build();
	}
	
	public static JSONArray parseCommand(String devicetype , byte[] cmd)
	{
		CommandValue cv = CommandParser.parse(cmd);
		
		if ( cv == null )
			return null ;
		
		StatusTranslate st = ReportTranslateMap.get(Utils.makekey(devicetype, cv.getCommandclass(), cv.getCommand(), cv.getValue()));
		if ( st == null )
			return null ;
		
		JSONArray ja = new JSONArray();
		if ( cv.getChannelid() == 0 )
			ja.add(createControllArgu(st.getAction() , st.getName(), st.getValue()));
		else 
			ja.add(createControllArgu(st.getAction() , cv.getChannelid(), st.getName() , st.getValue()));
		
		return ja ;
	}
	
	@Deprecated
	public static JSONArray parseCommand(byte[] cmd )
	{
		JSONArray ja = new JSONArray();
		
		if ( cmd == null || cmd.length == 0 )
			return ja;
		
		int cmdcls = cmd[0] & 0xff ;
		int command = cmd[1] & 0xff ;
		if ( cmdcls == 0x20 && command == 0x01 )
		{
			if ( ( cmd[2] & 0xff ) == 0xff )
				ja.add(createControllArgu("setAlarm" , "alarm" , 1));
			else 
				ja.add(createControllArgu("setAlarm" , "alarm" , 0));
		}
		else if ( cmdcls == 0x62 && command == 0x01 )
		{
			if ( ( cmd[2] & 0xff ) == 0xff )
				ja.add(createControllArgu("setLock" , "lock" , 0));
			else 
				ja.add(createControllArgu("setLock" , "lock" , 1));
		}
		else if ( cmdcls == 0x60 && command == 0x0d )
		{
			int channel = cmd[3] & 0xff;
			cmdcls = cmd[4] & 0xff ;
			command = cmd[5] & 0xff ;
			
			if ( cmdcls == 0x25 && command == 0x01 )
			{
				if ( ( cmd[6] & 0xff ) == 0xff )
					ja.add(createControllArgu("setSwitch" , channel , "Switch" , 1));
				else 
					ja.add(createControllArgu("setSwitch" , channel , "Switch" , 0));
			}
			
		}
		return ja;
	}
	
	private static JSONObject createControllArgu(String action , String argname , int argvalue)
	{
		JSONObject json = new JSONObject();
		json.put("action",action);
		JSONObject arg = new JSONObject();
		arg.put(argname, argvalue);
		json.put("argumentList", arg);
		return json;
	}
	
	private static JSONObject createControllArgu(String action , int channel , String argname , int argvalue)
	{
		JSONObject json = new JSONObject();
		json.put("action",action);
		JSONObject arg = new JSONObject();
		arg.put(argname, argvalue);
		arg.put("Channel", channel);
		json.put("argumentList", arg);
		return json;
	}
	
	public static byte[] synchronizeRequest(AsiainfoMessage message , String deviceid , int sequence , int timeoutsecond )
	{
		byte[] result = null ;
		try {
			String key = deviceid + String.valueOf(sequence);
			SynchronizeRequestManager.regist(key, "Asininfo_request");
			SynchronizeRequestManager.regist(message.getSequence(), "Asininfo_request");
			
			WebSocketWrap.writeMessage(message);
			
			CommonResponse response = (CommonResponse) SynchronizeRequestManager.getResponse(message.getSequence(), "Asininfo_request");
			if ( response != null &&  response.getReplyResult() == 0 )
			{
				Object r = SynchronizeRequestManager.getResponse(key, "Asininfo_request" , timeoutsecond);
				if ( r != null )
					result = (byte[]) r;
			}
		} catch (KeyCrashException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (TimeoutException e) {
			log.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		
		SynchronizeRequestManager.remove(deviceid+sequence, "Asininfo_request");
		SynchronizeRequestManager.remove(message.getSequence(), "Asininfo_request");
		
		return result;
	}
	
	public static int getSequence()
	{
		synchronized(AsiainfoHttpHelper.class)
		{
			if ( sequence < 0 || sequence > 256 * 256 - 100 )// Integer.MAX_VALUE - 100 )
				sequence = 0 ;
			sequence ++ ;
			return sequence ;
		}
	}
}
