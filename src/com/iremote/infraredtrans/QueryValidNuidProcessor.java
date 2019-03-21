package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.List;

import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.service.ZWaveDeviceService;

public class QueryValidNuidProcessor implements IRemoteRequestProcessor {
	
	public static final boolean[] DEVICE_TYPE_WAKEUP_MAP = new boolean[]{false 	/* 0*/ ,
																	true 	/* 1:smoke*/ , 
																	true  	/*2:water*/ , 
																	false  	/*3:gas*/, 
																	true  	/*4:door sensor*/, 
																	false  	/*5:door lock*/,
																	true   	/*6:move*/ ,
																	false   /*7:switch*/ ,
																	false   /*8:2 channel switch*/ ,
																	false   /*9:3 channel switch*/ ,
																	false   /*10:alarm*/ ,
																	false 	/*11:outlet*/ ,
																	false   /*12:robot*/ ,
																	false   /*13:curtain*/,
																	false 	/*14:air conditioning*/,
																	false 	/*15:power meter*/, 
																	true 	/*16:SOS alarm*/,
																	false 	/*17:water meter*/,
																	false 	/*18:peep hole*/,
																	false 	/*19:lock */,
																	false 	/*20:dimmer*/,
																	false 	/*21:not define*/,
																	false 	/*22:access controller*/,
																	false 	/*23:air quality*/,
																	false 	/*24:one channel scene panel*/,
																	false 	/*25:two channel scene panel*/,
																	false 	/*26:three channel scene panel*/,
																	false 	/*27:four channel scene panel*/,
																	false 	/*28:air condition(out)*/,
																	false 	/*29:Ventilation System*/,
																	false 	/*30:Home Theater */,
																	true 	/*31:power free switch*/,
																	true 	/*32:power free switch*/,
																	true 	/*33:power free switch*/,
																	true 	/*34:power free switch*/,
																	true 	/*35:power free switch*/,
																	false 	/*36:*/,
																	false 	/*37:*/,
																	false 	/*38:*/,
																	false 	/*39:*/,
																	false 	/*40:*/,
																	false 	/*41:*/,
																	false 	/*42:*/,
																	false 	/*43:*/,
																	false 	/*44:*/,
																	false 	/*45:*/,
																	false 	/*46:*/,
																	false 	/*47:*/,
																	true 	/*48:*/,
																	true 	/*49:*/,
																	true 	/*50:*/,
																	true 	/*51:*/,
																	true 	/*52:*/,
																	}; 
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		String deviceid = nbc.getDeviceid();
		
		ZWaveDeviceService svr = new ZWaveDeviceService();
		List<ZWaveDevice> lst = svr.querybydeviceid(deviceid);
		
		if ( lst == null )
			lst = new ArrayList<ZWaveDevice>();
		
		byte[] zid = new byte[lst.size() * 2];
		
		int i = 0 ;
		for ( ZWaveDevice z : lst )
		{
			if ( z.getNuid() > 256 ) //not a stand zwave device
				continue;
			zid[i * 2 ] = (byte)z.getNuid();
			if ( isWakeupDevice(z))
				zid[i * 2 + 1 ] = 1;  //wake up device
			else 
				zid[i * 2 + 1 ] = 0 ;
			i ++ ;
		}
		
		CommandTlv rst = new CommandTlv(30 , 21);
		
		rst.addUnit(new TlvIntUnit(1,0,2));
		rst.addUnit(new TlvByteUnit(77 , zid));
		
		return rst;
	}

	private boolean isWakeupDevice(ZWaveDevice zd)
	{
		int type = 0 ;
		if ( zd != null && zd.getDevicetype() != null && zd.getDevicetype().length() > 0)
			type = Integer.valueOf(zd.getDevicetype());
		if ( type >= DEVICE_TYPE_WAKEUP_MAP.length )
			type = 0 ;
		return DEVICE_TYPE_WAKEUP_MAP[type];
	}
}
