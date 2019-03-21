package com.iremote.infraredtrans.tlv;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.common.JWStringUtils;
import com.iremote.common.Utils;
import com.iremote.interceptor.RemoteServer;

public class CommandTlvTest
{
	public static void main(String arg[])
	{
		CommandTlv ct = new CommandTlv(3 , 11);
		ct.addUnit(new TlvByteUnit(13 , "Bluemobi".getBytes()));
		ct.addUnit(new TlvByteUnit(27 , "@BM#2014$".getBytes()));
		
		byte[] b = ct.getByte();
		b = Utils.wrapRequest(b);
		System.out.println(JWStringUtils.toHexString(b));
		
		Utils.print("", ct.getByte());
	}
	
	

}
