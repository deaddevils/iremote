package com.iremote.action.data;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceTest {

	public static void main(String arg[])
	{
		ResourceBundle res_ch = ResourceBundle.getBundle("setDeviceName", Locale.CHINA);
		ResourceBundle res_en = ResourceBundle.getBundle("setDeviceName", Locale.US);
		
		for ( Enumeration<String> it = res_ch.getKeys() ; it.hasMoreElements(); ) 
		{
			String key = it.nextElement();
			System.out.println(String.format("%s\t%s\t%s", key , res_ch.getString(key) , res_en.getString(key)));
		}
	}
}
