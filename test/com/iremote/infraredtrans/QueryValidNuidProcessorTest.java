package com.iremote.infraredtrans;

import java.util.ArrayList;
import java.util.List;

import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

public class QueryValidNuidProcessorTest {

	private static boolean[] DEVICE_TYPE_WAKEUP_MAP = new boolean[]{true /* 0*/ ,
		true /* 1:smoke*/ , 
		true  /*2:water*/ , 
		true  /*3:gas*/, 
		true  /*4:door sensor*/, 
		false  /*5:door lock*/,
		true   /*6:move*/ ,
		false   /*7:switch*/ ,
		false   /*8:2 channel switch*/ ,
		false   /*9:3 chanel switch*/ ,
		false   /*10:alarm*/ ,
		false /*11:outlet*/ ,
		false   /*12:robot*/ ,}; 

	
	public static void main(String arg[])
	{
		String deviceid = "iRemote2005000000010";
		
		ZWaveDeviceService svr = new ZWaveDeviceService();
		List<ZWaveDevice> lst = svr.querybydeviceid(deviceid);
		
		if ( lst == null )
			lst = new ArrayList<ZWaveDevice>();
		
		byte[] zid = new byte[lst.size() * 2];
		
		int i = 0 ;
		for ( ZWaveDevice z : lst )
		{
			zid[i * 2 ] = (byte)z.getNuid();
			if ( isWakeupDevice(z))
				zid[i * 2 + 1 ] = 1;  //wake up device
			else 
				zid[i * 2 + 1 ] = 0 ;
			i ++ ;
		}
		
		System.out.println("");
	}
	
	private static boolean isWakeupDevice(ZWaveDevice zd)
	{
		int type = 0 ;
		if ( zd != null && zd.getDevicetype() != null && zd.getDevicetype().length() > 0)
			type = Integer.valueOf(zd.getDevicetype());
		if ( type > DEVICE_TYPE_WAKEUP_MAP.length )
			type = 0 ;
		return DEVICE_TYPE_WAKEUP_MAP[type];
	}
}
