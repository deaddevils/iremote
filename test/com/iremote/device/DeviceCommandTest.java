package com.iremote.device;

import net.sf.json.JSONArray;

import org.apache.commons.codec.binary.Base64;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.RemoteHandler;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
@SuppressWarnings("unused")
public class DeviceCommandTest {

	public static void main(String arg[])
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvIntUnit(71 , 17, 1)); // nuid 
		ct.addUnit(new TlvIntUnit(72 , 0, 1)); // wakeup ?
		ct.addUnit(new TlvIntUnit(79 , 4, 1));  //association 
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x20, 0x02})); //basic get
		
		
		Utils.print("" , ct.getByte(), ct.getTotalLength());
//		Utils.print("", Base64.decodeBase64("AEYAA0ABAABKAANAAwAARwABDgBIAAEAAE8AAQI=")); 
		Utils.print("", Base64.decodeBase64("BAEAFgAoABIwAQGFFAICAQABAgUAAQIQ/+oA"));
//		Utils.print("", Base64.decodeBase64("AEYAAyUBAABIAAEAAEoAAyUDAABHAAEP"));
//		Utils.print("", Base64.decodeBase64("BAEADgAoAAowADwM8wAAAABrsQ=="));
//		Utils.print("", Base64.decodeBase64("MAABHuEA/wAALw=="));
//		Utils.print("", Base64.decodeBase64("MAAj2iVNRVRXjw=="));

		
	}
	

}
