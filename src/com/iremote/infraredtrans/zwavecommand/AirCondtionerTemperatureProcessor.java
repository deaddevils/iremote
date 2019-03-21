package com.iremote.infraredtrans.zwavecommand;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class AirCondtionerTemperatureProcessor extends ZWaveReportBaseProcessor
{
	
	public AirCondtionerTemperatureProcessor() {
		super();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		//int status = zrb.getCommandvalue().getValue() ;
		int scale = ( zrb.getCmd()[3] & 0x18 ) >> 3 ; // 0x18 : 00011000
		int precision = ( zrb.getCmd()[3] & 0xE0 ) >> 5; //0xE0 : 11100000
		int size = (zrb.getCmd()[3] & 0x07 ) ; //0x07 : 00000111
		
		int status = zrb.getCmd()[4] ;
		for ( int i = 1 ; i < size ; i ++ )
		{
			status = status << 8 ;
			status = status | ( zrb.getCmd()[4+i] & 0xff)  ;
		}
		
		float fstatus = (float)(status * 1f / Math.pow(10, precision));
		
		float c , f;
		
		if ( scale == 0 )
		{
			c = fstatus;
			f = Utils.celsius2fahrenheit(c);
		}
		else 
		{
			f = fstatus;
			c = Utils.fahrenheit2celsius(f);
		}
		
		setDeviceTemperature(c , f);
	}
	
	protected void setDeviceTemperature(float c , float f)
	{
		String s = zrb.getDevice().getStatuses();
		if ( s == null )
			s = Utils.getDeviceDefaultStatuses(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER);
		JSONArray ja = JSON.parseArray(s);
		ja.set(2 , c);
		ja.set(3 , f);
		zrb.getDevice().setStatuses(ja.toJSONString());
	}
	
	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
	
	public static void main(String arg[])
	{
		byte[] cmd = new byte[]{67, 3 ,1 ,66, 10, (byte)240};
		int scale = ( cmd[3] & 0x18 ) >> 3 ; // 0x18 : 00011000
		int precision = ( cmd[3] & 0xE0 ) >> 5; //0xE0 : 11100000
		int size = (cmd[3] & 0x07 ) ; //0x07 : 00000111
		
		int status = cmd[4] ;
		for ( int i = 1 ; i < size ; i ++ )
		{
			status = status << 8 ;
			status = status | ( cmd[4+i] & 0xff)  ;
		}
		
		float fstatus = (float)(status * 1f / Math.pow(10, precision));
		
		float c , f;
		
		if ( scale == 0 )
		{
			c = fstatus;
			f = Utils.celsius2fahrenheit(c);
		}
		else 
		{
			f = fstatus;
			c = Utils.fahrenheit2celsius(f);
		}
	}
	
//	public static void main(String arg[])
//	{
//		byte[][] bb = new byte[][]{{(byte)0x80},{(byte)0x7F},{(byte)0xfF},{(byte)0xfe},{(byte)0x02},{(byte)0x01},
//									{(byte)0x80,(byte)0x00} , {(byte)0x7F,(byte)0xff},{(byte)0xFF,(byte)0xff},{(byte)0xFF,(byte)0xfe},{(byte)0x00,(byte)0x01},
//									{(byte)0x80,(byte)0x00,(byte)0x00,(byte)0x00} , {(byte)0x7F,(byte)0xff,(byte)0xff,(byte)0xff},
//									{(byte)0xFF,(byte)0xff,(byte)0xff,(byte)0xff},{(byte)0xFF,(byte)0xff,(byte)0xff,(byte)0xfe}};
//		for ( int j = 0 ; j < bb.length ; j ++ )
//		{
//			byte[] b = bb[j] ;
//			int status = b[0] ;
//			for ( int i = 1 ; i < b.length ; i ++ )
//			{
//				status = status << 8 ;
//				status = status | ( b[i] & 0xff)  ;
//			}
//			
//			System.out.println(status);
//		}
//	}
}
