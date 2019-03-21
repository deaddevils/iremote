package com.iremote.action.qrcode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.Utils;

public class GatewayDeviceQrcodeCreator
{
	public static void main1(String arg[])
	{
		String device[] = new String[]{"iRemote3006100001389"};
		String type[] = new String[]{"lockgateway"};
		
		for ( int i = 0 ; i < device.length ; i ++ )
		{
			JSONObject json = new JSONObject();
			json.put("type", type[i]);
			json.put("qid", Utils.createsecuritytoken(20));
			
			PowerFreeDeviceQrcodeCreator.createCode(json.toJSONString() , String.format("e:\\qrcode\\%s.png", device[i]));
			
			System.out.println(String.format("insert into gatewayinfo (deviceid , qrcodekey , gatewaytype) values ( '%s' , '%s' , '%s');" ,device[i] ,json.getString("qid"), type[i]));
		}
	}
	
	public static void main3(String arg[])
	{
		Set<String> s = new HashSet<String>();
		for ( int i = 0  ; i < 100 ; i ++ )
		{
			s.add(Utils.inttoString(1000 + i ));
		}
		for ( String str : s)
			System.out.println(str);
	}
	
	public static void main(String arg[])
	{
		try
		{
			create("gateway" ,"80052" , 1 , 1000);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void create(String type , String productorcode , int base , int count ) throws IOException
	{
		FileOutputStream f1 = new FileOutputStream((String.format("e:\\qrcode\\batchcode\\%s_%s_%d_%d.qrc", productorcode , type , base , count)));
		FileOutputStream f2 = new FileOutputStream(String.format("e:\\qrcode\\batchcode\\%s_%s_%d_%d.sql", productorcode , type , base , count));
		
		//Random r = new Random(System.currentTimeMillis());
		for ( int i = 0 ; i < count ; i ++ )
		{
			//Utils.sleep(r.nextInt(10) + 1 );
			String deviceid = String.format("iRemote%s000%05d",productorcode, i + base);
			JSONObject json = new JSONObject();
			json.put("type", type);
			String qid = String.format("%s%s", Utils.inttoString(i+base), Utils.createsecuritytoken(20));
			json.put("qid", qid);
			
			f1.write(deviceid.getBytes());
			f1.write("  ".getBytes());
			f1.write(json.toString().getBytes());
			f1.write("\n".getBytes());
			
			f2.write(String.format("insert into gatewayinfo (deviceid , qrcodekey , gatewaytype) values ( '%s' , '%s' , '%s');" ,deviceid ,json.getString("qid") , type).getBytes());
			f2.write("\n".getBytes());
		}
		f1.close();
		f2.close();
	}
	

}
