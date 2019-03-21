package com.iremote.event.gateway;

import com.alibaba.fastjson.JSON;
import com.iremote.test.db.Db;

public class AutoCreateDeviceTest
{
	
	public static void main(String arg[])
	{
		System.out.println(1);
		Db.init();
		String str = "{\"deviceid\":\"iRemote3006200000014\",\"eventtime\":1489213113118,\"newownerid\":3,\"newownerphonenumber\":\"13266836350\",\"oldownerid\":0,\"remote\":{\"createtime\":1487211201000,\"deviceid\":\"iRemote3006200000014\",\"homeid\":1,\"ip\":\"192.168.1.129\",\"iversion\":31,\"lastupdatetime\":1489213113034,\"latitude\":0,\"longitude\":0,\"mac\":\"ACCF23D4B2C7\",\"name\":\"000014\",\"network\":0,\"networkintensity\":100,\"phonenumber\":\"13266836350\",\"phoneuserid\":3,\"platform\":0,\"powertype\":0,\"remotetype\":22,\"ssid\":\"JWZH\",\"status\":1,\"version\":\"1.2.20btz\"},\"taskIndentify\":1489213113118}";
		
		AutoCreateDevice ap = JSON.parseObject(str, AutoCreateDevice.class);
		ap.run();
		Db.commit();
		
	}
}
