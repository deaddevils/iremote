package com.iremote.common.commandclass;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.JWStringUtils;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

public class CommandUtilTest
{
	public static void main(String arg[])
	{
		CommandTlv ct = CommandUtil.createLockCommand(11201, (byte)1);
		byte[] b = ct.getByte();
		int c = 0 ;
		for ( int i = 0 ; i < b.length - 1 ; i ++ )
			c =  ( c ^ b[i]);
		b[b.length -1] = (byte)c;
		
		Utils.print("", b);
		System.out.println(JWStringUtils.toHexString(b));
	}

	public static void main3(String arg[])
	{
		//CommandUtil.createColorSwitchCommand(2, new Byte[]{null, 127 , 23 , 45 ,74, null , null , null , null , null ,56 } , "");
		
		Byte temperature = (byte)182;
		Byte light = 100;
		Integer cold = 0 , warn = 0 ;
		
		if ( temperature != null && light != null  )
		{
			if((temperature & 0xff)<128)
			{
			  cold=((255-(temperature & 0xff))*(light & 0xff))/255;
			  warn=(light & 0xff)-cold;
			}
			else
			{
			  warn=(temperature&0xff)*(light & 0xff)/255;
			  cold=(light & 0xff)-warn;
			 }
		}
		
		System.out.println(warn);
		System.out.println(cold);
	}
	
	public static void main2(String arg[])
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{32 , 2}));
		ct.addUnit(new TlvIntUnit(71 , 31 , 1));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		
		Utils.print("", ct.getByte());
	}

	
	public static void main1(String arg[])
	{
			
		CommandTlv ct = CommandUtil.createDoorlockGetVersionComand(11201);
		Utils.print("", ct.getByte());
		
		JSONArray ja = (JSONArray)JSONArray.toJSON(ct.getByte());
		System.out.println(ja.toJSONString());
		
		byte[] b = ct.getByte();
		System.out.print("[");
		for ( int i = 0 ; i < b.length ; i ++  )
			System.out.print( String.format("%d," , b[i] & 0xff));
		System.out.println("]");
		
		System.out.println(JWStringUtils.toHexString(new byte[]{2 ,28 ,16 ,0 ,16, 1}));
		
		showQueryProductorCommand(19);
	}
	
	private static void showQueryProductorCommand(int nuid)
	{
		CommandTlv ct = CommandUtil.createZWaveManufactureCommand(nuid);
		byte[] b = ct.getByte();
		
		System.out.print("[");
		for ( int i = 4 ; i < b.length ; i ++  )
			System.out.print( String.format("%d," , b[i] & 0xff));
		System.out.println("]");
	}
}
