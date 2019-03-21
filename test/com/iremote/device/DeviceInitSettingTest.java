package com.iremote.device;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DeviceInitSettingTest 
{
	public static void main(String arg[])
	{
		String str = readfile("e:\\mid2type_new.conf");
		
		JSONObject json = JSON.parseObject(str);
		
		JSONArray ja = json.getJSONArray("typeArray");
		
		for ( int i = 0 ; i < ja.size() ; i ++)
		{
			JSONObject dt = ja.getJSONObject(i);
			String type = dt.getString("typeId");
			JSONArray jads = dt.getJSONArray("devices");
			
			for ( int j = 0 ; j < jads.size() ; j ++)
			{
				JSONObject jds = jads.getJSONObject(j);
				
				if ( jds.containsKey("initCmds"))
					System.out.println(String.format("Insert into deviceinitsetting(mid , manufacture,devicetype,initcmds) values ( '%s' , '%s' , '%s' , '%s'); ", jds.getString("mid").toLowerCase().trim() , jds.getString("manufacture") , type , jds.getString("initCmds")));
				else 
					System.out.println(String.format("Insert into deviceinitsetting(mid , manufacture,devicetype,initcmds) values ( '%s' , '%s' , '%s' , null); ", jds.getString("mid").toLowerCase().trim() , jds.getString("manufacture") , type ));
			}
		}
	}
	
	public static String readfile(String filename)
	{
		File f =new File(filename);
		
		try {
			InputStream is = new FileInputStream(f);
			byte b[] = new byte[is.available()];
			is.read(b);
			is.close();
			
			if ( b[0] != (byte)'{')
			{
				int i = 0 ;
				for (  ; i < b.length ; i ++ )
					if ( b[i] == (byte)'{')
						break;
				byte[] b2 = new byte[b.length - i ];
				System.arraycopy(b, i, b2, 0, b2.length);
				b = b2 ;
			}
			
			return new String(b);
		} catch (Throwable e) {
			e.printStackTrace();
		} 
		return null ;
	}
}
