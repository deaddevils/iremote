package com.iremote.infraredtrans.zwavecommand.doorlock;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.infraredtrans.ZWaveReportProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.test.db.Db;
import com.iremote.test.db.Env;
import com.iremote.test.mock.TestConnectionContext;

public class DoorlockSelfDefineOpenwithPwandnoIdReportProcessorTest
{
	public static void main(String arg[])
	{
		Env.getInstance().need();
		Db.init();
		
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
				
		//byte cmd[] = new byte[]{30,(byte)9,(byte)0,(byte)30,(byte)0,(byte)71,(byte)0,(byte)1,(byte)4,(byte)0,(byte)70,(byte)0,(byte)7,(byte)98,(byte)3,(byte)255,(byte)0,(byte)0,(byte)254,(byte)254,(byte)0,(byte)31,(byte)0,(byte)2,(byte)0,(byte)57,(byte)0,(byte)104,(byte)0,(byte)4,(byte)89,(byte)184,(byte)170,(byte)83,(byte)201};
		byte cmd[] = new byte[]{30,(byte)9,(byte)0,(byte)45,(byte)0,(byte)71,(byte)0,(byte)1,(byte)4,(byte)0,(byte)70,(byte)0,(byte)22,(byte)128,(byte)7,(byte)0,(byte)177,(byte)16,(byte)1,(byte)3,(byte)0,(byte)23,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)18,(byte)7,(byte)9,(byte)13,(byte)17,(byte)27,(byte)0,(byte)31,(byte)0,(byte)2,(byte)1,(byte)79,(byte)0,(byte)104,(byte)0,(byte)4,(byte)89,(byte)184,(byte)250,(byte)142,(byte)167};
		
		try
		{
			pro.process(cmd, new TestConnectionContext("iRemote2005000000272"));
		} 
		catch (BufferOverflowException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		Db.commit();
	}
}
