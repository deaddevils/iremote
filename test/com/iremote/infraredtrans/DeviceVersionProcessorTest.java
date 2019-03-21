package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.mock.TestConnectionContext;
import com.iremote.test.db.Db;

public class DeviceVersionProcessorTest
{
	public static void main(String arg[])
	{
		Db.init();
		DeviceVersionProcessor dvp = new DeviceVersionProcessor();
				
		try
		{
			dvp.process(new byte[]{(byte)254,(byte)244,0 ,9 ,0 ,4 ,0 ,5 ,48, 46, 49, 46, 57, 58}, new TestConnectionContext("iRemote3006100000242"));
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
