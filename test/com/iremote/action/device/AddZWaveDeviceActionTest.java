package com.iremote.action.device;

import java.util.Locale;

import com.iremote.test.db.Db;

public class AddZWaveDeviceActionTest 
{
	public static void main(String arg[])
	{
		System.out.println(Locale.CHINA.toString());
		 Locale temp = new Locale("vi", "VM");
		System.out.println(temp.toString());
		
		//vi_VN
		
		  Locale[] localeList=Locale.getAvailableLocales();
		  for (int i=0;i<localeList.length;i++)
		  {
		   System.out.println(localeList[i].toString());
		  }
	}
	
	public static void main1(String arg[])
	{
		byte[] b = new byte[]{105,(byte)9,(byte)0,(byte)37,(byte)0,(byte)71,(byte)0,(byte)1,(byte)16,(byte)0,(byte)80,(byte)0,(byte)5,(byte)83,(byte)156,(byte)4,(byte)32,(byte)1,(byte)0,(byte)81,(byte)0,(byte)6,(byte)2,(byte)28,(byte)128,(byte)0,(byte)16,(byte)0,(byte)0,(byte)82,(byte)0,(byte)9,(byte)32,(byte)48,(byte)156,(byte)133,(byte)112,(byte)114,(byte)134,(byte)132,(byte)128,(byte)167};
	
		Db.init();
		AddZWaveDeviceAction action = new AddZWaveDeviceAction();
		action.setDeviceid("iRemote4005000001050");
		action.setNuid(15);

		int rst = action.addZWaveDevice(b);
		
		System.out.println(rst);
		Db.commit();
	}
}
