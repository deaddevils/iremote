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

public class DoorlockDoubleAuthReportProcessorText
{
	public static void main(String arg[])
	{
		Env.getInstance().need();
		Db.init();
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
				
		
		int nuid = 9 ;
		CommandTlv ct = new CommandTlv(30 , 9);
		ct.addUnit(new TlvIntUnit(71 , nuid , 2));
		ct.addUnit(new TlvIntUnit(104 , (int)(System.currentTimeMillis()/1000) , 4));
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x80,0x07,0x00,(byte)0xB1,0x10,0x01,0x03,0x00,0x21,0x16,0x10}));
		
		byte cmd[] = ct.getByte();
		
		try
		{
			pro.process(cmd, new TestConnectionContext("iRemote3006200000010"));
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