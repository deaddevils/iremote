package com.iremote.test.unittest.dataprivilege;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.http.HttpUtil;

import junit.framework.Assert;

public class ThirdpartPrivilegeTest
{
	private String baseurl = "http://127.0.0.1:8080/iremote/" ;
	
	private String token = "0ceff3bdfccf45b6aa89f23b2cd0378e647325" ;
	private int respectresult = ErrorCodeDefine.TIME_OUT;
	private int respectresult2 = ErrorCodeDefine.DEVICE_OFFLINE;

//	private String token = "6dcc8c0a2dce484b8b3c892053b1bee1411892" ;
//	private int respectresult = ErrorCodeDefine.NO_PRIVILEGE;
//	private int respectresult2 = ErrorCodeDefine.NO_PRIVILEGE;
	
	@Test
	public void checkUnlockProcess()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/unlock",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void checkUnlockProcess2()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("lockid", 5);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/unlock",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void setlockpassword()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		json.put("password", "124534");
		json.put("validfrom", "2016-01-01 00:00:00");
		json.put("validthrough", "2099-01-01 00:00:00");
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/setlockpassword",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult2, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void setlockpassword2()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("lockid", 5);
		json.put("password", "124534");
		json.put("validfrom", "2016-01-01 00:00:00");
		json.put("validthrough", "2099-01-01 00:00:00");
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/setlockpassword",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult2, rst.getIntValue("resultCode"));
	}
	
	
	@Test
	public void setlockuserpassword()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		json.put("password", "124534");
		json.put("validfrom", "2016-01-01 00:00:00");
		json.put("validthrough", "2099-01-01 00:00:00");
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/setlockuserpassword",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult2, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void setlockuserpassword2()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("lockid", 5);
		json.put("password", "124534");
		json.put("validfrom", "2016-01-01 00:00:00");
		json.put("validthrough", "2099-01-01 00:00:00");
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/setlockuserpassword",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult2, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void setlockadminpassword()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		json.put("password", "124534");
		json.put("validfrom", "2016-01-01 00:00:00");
		json.put("validthrough", "2099-01-01 00:00:00");
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/setlockadminpassword",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult2, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void setlockadminpassword2()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("lockid", 5);
		json.put("password", "124534");
		json.put("validfrom", "2016-01-01 00:00:00");
		json.put("validthrough", "2099-01-01 00:00:00");
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/setlockadminpassword",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult2, rst.getIntValue("resultCode"));
	}
	
	
	@Test
	public void deletelockpassword()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/deletelockpassword",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult2, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void deletelockpassword2()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("lockid", 5);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/deletelockpassword",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult2, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void setlockfingerprintuser()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		json.put("password", "124534");
		json.put("validfrom", "2016-01-01 00:00:00");
		json.put("validthrough", "2099-01-01 00:00:00");
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/setlockfingerprintuser",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult2, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void deletelockfingerprintuser()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		json.put("usercode", "1");
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/deletelockfingerprintuser",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void deletelockfingerprintuser2()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("lockid", 5);
		json.put("usercode", "1");
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/deletelockfingerprintuser",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void opendevice()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/opendevice",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void opendevice2()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("deviceid", "iRemote2005000000560");
		json.put("nuid", 6);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/opendevice",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void closedevice()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/opendevice",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void closedevice2()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("deviceid", "iRemote2005000000560");
		json.put("nuid", 6);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/closedevice",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void setdevicestatus()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("zwavedeviceid", 5);
		json.put("status", 50);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/setdevicestatus",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
	@Test
	public void setdevicestatus2()
	{
		JSONObject json = new JSONObject();
		
		json.put("token", token);
		json.put("deviceid", "iRemote2005000000560");
		json.put("nuid", 6);
		json.put("status", 50);
		String str = HttpUtil.httprequest(baseurl + "thirdpart/zufang/setdevicestatus",  json);
		JSONObject rst = JSON.parseObject(str);
		
		Assert.assertEquals(respectresult, rst.getIntValue("resultCode"));
	}
	
}
