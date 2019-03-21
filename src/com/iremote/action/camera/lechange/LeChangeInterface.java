package com.iremote.action.camera.lechange;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.Utils;

public class LeChangeInterface 
{
	private static Log log = LogFactory.getLog(LeChangeInterface.class);
	
	private static  final String LECHANGE_BASE_URL = "https://openapi.lechange.cn:443/openapi/";
	private static  final String LECHANGE_ABROAD_BASE_URL = "https://openapi.easy4ip.com:443/openapi/";
	private static final String ver = "1.0";
	private static Integer nonce_sequence = 0 ;
	
	protected String appid ;
	protected String appSecret;
//	protected String admin ;
	protected String url = "http://dev.isurpass.com.cn/iremote/camera/lechange/lechangewarningreport";
	private long time = System.currentTimeMillis()/1000;
	private String sign;
	private String devicetype = IRemoteConstantDefine.CAMERA_DEVICE_TYPE_DOMESTIC;

	public LeChangeInterface(String appid, String appSecret, String admin)
	{
		super();
		this.appid = appid;
		this.appSecret = appSecret;
//		this.admin = admin;
	}

	public LeChangeInterface(String appid, String appSecret, String admin, String devicetype)
	{
		super();
		this.appid = appid;
		this.appSecret = appSecret;
		//		this.admin = admin;
		this.devicetype = devicetype;
	}

//	public JSONObject accessToken() 
//	{
//		JSONObject pm = new JSONObject();
//		pm.put("phone", admin);
//		
//		return leChangeRequest("accessToken" , pm);
//	}

//	public JSONObject registCallback()
//	{
//		JSONObject json = this.accessToken();
//		
//		if ( !ErrorCodeDefine.SUCCESS_STR.equals(this.getResultCode(json)))
//			return json ;
//		String at = this.getData(json, "accessToken");
//		
//		JSONObject pm = new JSONObject();
//		pm.put("token", at);
//		pm.put("status", "on");
//		pm.put("callbackUrl", url);
//		pm.put("callbackFlag", "alarm,deviceStatus");
//
//		return leChangeRequest("setMessageCallback" , pm) ;
//	}
	public JSONObject accessToken() {
		return leChangeRequest("accessToken", new JSONObject());
	}

	public JSONObject userToken(String phonenumber) 
	{
		JSONObject pm = new JSONObject();
		pm.put("phone", phonenumber);
		pm.put("email", phonenumber);
		return  leChangeRequest("userToken" , pm);
	}

	public JSONObject userBindSms(String phonenumber) 
	{
		JSONObject pm = new JSONObject();
		pm.put("phone", phonenumber);

		return leChangeRequest("userBindSms" , pm);
	}

	public JSONObject userBind(String phonenumber ,String smsCode, String email)
	{
		JSONObject pm = new JSONObject();
		pm.put("phone", phonenumber);
		pm.put("smsCode", smsCode);
		pm.put("email", email);

		return leChangeRequest("userBind" , pm);
	}
	
	public JSONObject bindDevice(String camerauuid , String code , String token)
	{
		JSONObject pm = new JSONObject();
		pm.put("deviceId", camerauuid);
		pm.put("code", code);
		pm.put("token", token);
		
		return leChangeRequest("bindDevice" , pm);
	}
	
	public JSONObject unbindDevice(String camerauuid , String token)
	{
		JSONObject pm = new JSONObject();
		pm.put("deviceId", camerauuid);
		pm.put("token", token);
		
		return leChangeRequest("unBindDevice" , pm);
	}
	
	public JSONObject queryDeviceInfo(String camerauuid , String token)
	{
		JSONObject pm = new JSONObject();
		pm.put("deviceId", camerauuid);
		pm.put("token", token);
		
		return leChangeRequest("bindDeviceInfo" , pm);
	}
	
	public JSONObject queryCurrentDeviceWifi(String camerauuid , String token)
	{
		JSONObject pm = new JSONObject();
		pm.put("deviceId", camerauuid);
		pm.put("token", token);
		
		return leChangeRequest("currentDeviceWifi" , pm);
	}
	
	public JSONObject queryBreathingLightStatus(String camerauuid , String token)
	{
		JSONObject pm = new JSONObject();
		pm.put("deviceId", camerauuid);
		pm.put("token", token);
		
		return leChangeRequest("breathingLightStatus" , pm);
	}
	
	public JSONObject queryFrameReverseStatus(String camerauuid , String token)
	{
		JSONObject pm = new JSONObject();
		pm.put("deviceId", camerauuid);
		pm.put("token", token);
		pm.put("channelId", 0);
		
		return leChangeRequest("frameReverseStatus" , pm);
	}
	
	public JSONObject queryDeviceAlarmPlan(String camerauuid , String token)
	{
		JSONObject pm = new JSONObject();
		pm.put("deviceId", camerauuid);
		pm.put("token", token);
		pm.put("channelId", 0);
		
		return leChangeRequest("deviceAlarmPlan" , pm);
	}
	
    public JSONObject leChangeRequest(String rqmethod, JSONObject map) 
    {
    	JSONObject parameter = paramsInit(map);
    	String url = getLeChangeBaseUrl() + rqmethod;
        String json = JSON.toJSONString(parameter);
        ProtocolSocketFactory fcty = new DahualechangeSecureProtocolSocketFactory();
        Protocol.registerProtocol("https", new Protocol("https", fcty, 8080));
        HttpClient client = new HttpClient();
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        PostMethod method = new PostMethod(url);
        
        log.info(url);
        if ( log.isInfoEnabled())
        	log.info(json);
        
        String restult = "";
        JSONObject jsonObject = new JSONObject();
        try 
        {
            RequestEntity entity = new StringRequestEntity(json, "application/json", "UTF-8");
            method.setRequestEntity(entity);
            client.executeMethod(method);

            InputStream inputStream = method.getResponseBodyAsStream();
            restult = IOUtils.toString(inputStream, "UTF-8");
            log.info(restult);
            
            jsonObject = JSONObject.parseObject(restult);
        } 
        catch (Exception e) 
        {
        	log.error(e.getMessage() , e);
        } 
        finally 
        {
            method.releaseConnection();
        }
        return jsonObject;
    }

    private String getLeChangeBaseUrl(){
	    return (devicetype != null && IRemoteConstantDefine.CAMERA_DEVICE_TYPE_ABROAD.equals(devicetype))
			    ? LECHANGE_ABROAD_BASE_URL
			    : LECHANGE_BASE_URL;
    }

	public String getResultCode(JSONObject json)
	{
		return getResult(json , "code");
	}
	
	public String getResultMsg(JSONObject json)
	{
		return getResult(json , "msg");
	}
	
	protected String getResult(JSONObject json , String key)
	{
		if ( !json.containsKey("result") )
			return null ;
		JSONObject r = json.getJSONObject("result");
		if ( !r.containsKey(key))
			return null ;
		return r.getString(key);
	}
	
	public String getData(JSONObject json , String key)
	{
		if ( json == null )
			return null;
		if ( !json.containsKey("result")
				|| !json.getJSONObject("result").containsKey("data"))
			return null ;
		return json.getJSONObject("result").getJSONObject("data").getString(key);
	}
	
	public Integer getIntegerData(JSONObject json , String key)
	{
		String v = getData(json , key);
		if ( StringUtils.isBlank(v))
			return null ;
		return Integer.valueOf(v);
	}
	
    protected JSONObject paramsInit(JSONObject paramsMap) 
    {

        StringBuilder paramString = new StringBuilder();
        /*List<String> paramList = new ArrayList<String>();
        for (String key : paramsMap.keySet())
        {
            String param = key + ":" + paramsMap.get(key);
            paramList.add(param);
        }

        String[] params = paramList.toArray(new String[paramList.size()]);
        Arrays.sort(params);
        for (String param : params) 
        {
            paramString.append(param).append(",");
        }*/
        
        String nonce = Utils.createtoken();
        paramString.append("time:").append(time).append(",");
        paramString.append("nonce:").append(nonce).append(",");
        paramString.append("appSecret:").append(appSecret);
        sign = null;

        try 
        {
            sign = DigestUtils.md5Hex(paramString.toString().trim().getBytes("UTF-8"));
        } 
        catch (Throwable e) 
        {
        	log.error(e.getMessage() , e);
        }

        JSONObject systemMap = new JSONObject();
        systemMap.put("ver", ver);
        systemMap.put("sign", sign);
        systemMap.put("appId", appid);
        systemMap.put("nonce", nonce);
        systemMap.put("time", time);

        JSONObject rst = new JSONObject();
        rst.put("system", systemMap);
        rst.put("params", paramsMap);
        rst.put("id", getNextNonce());
        
        return rst;
    }
	
	private static int getNextNonce()
	{
		synchronized(nonce_sequence)
		{
			if ( nonce_sequence > 30000)
				nonce_sequence = 0 ;
			nonce_sequence ++ ;
			return nonce_sequence ;
		}
	}
}
