package com.iremote.action.doorlock;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Calendar;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.ZWaveReportProcessor;
import com.iremote.test.db.Db;
import com.iremote.test.db.Env;
import com.iremote.test.mock.TestConnectionContext;

public class OpenDoorlock
{
	public static void main(String arg[])
	{
		System.out.println(System.currentTimeMillis());
		Calendar ct = Calendar.getInstance();
		ct.setTimeInMillis(1520060181903L);
		byte[] b = new byte[]{(byte)0x8B,0x01,
							(byte)(ct.get(Calendar.YEAR) / 256 ),
							(byte)(ct.get(Calendar.YEAR) % 256 ),
							(byte)(ct.get(Calendar.MONTH) + 1 ),
							(byte)ct.get(Calendar.DAY_OF_MONTH),
							(byte)ct.get(Calendar.HOUR_OF_DAY),
							(byte)ct.get(Calendar.MINUTE),
							(byte)ct.get(Calendar.SECOND)};
		
		Utils.print("", b);
	}
	
	public static void main1(String arg[])
	{
		Env.getInstance().need();
		Db.init();
		ZWaveReportProcessor dvp = new ZWaveReportProcessor();
				
		try
		{
			//951753
			//128 7 0 177 16 1 3 0 21 3 0 0 0 0 0 0 0 17 3 11 10 50
			//30 9 0 45 0 71 0 1 9 0 70 0 22 128 7 0 177 16 1 3 0 21 3 0 0 0 0 0 0 0 17 3 11 10 50 0 31 0 2 0 14 0 104 0 4 88 195 101 234 93
			dvp.process(new byte[]{30,9,0,45,0,71,0,1,9,0,70,0,22,(byte)128,7,0,(byte)177,16,1,3,0,21,3,0,0,0,0,0,0,0,17,3,11,10,50,0,31,0,2,0,14,0,104,0,4,88,(byte)195,101,(byte)234,93}, new TestConnectionContext("iRemote3006200000010"));
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