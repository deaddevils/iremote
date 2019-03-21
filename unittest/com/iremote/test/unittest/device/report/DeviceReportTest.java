package com.iremote.test.unittest.device.report;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Date;

import com.iremote.infraredtrans.ZWaveReportProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.test.db.Db;
import com.iremote.test.mock.TestConnectionContext;

import junit.framework.Assert;

public class DeviceReportTest {

	public static void testReport(int nuid , byte[] report , String[] filter)
	{
		testReport(nuid , report , new String[][]{filter});
	}
	
	public static void testReport(int nuid , byte[] report , String[][] filter)
	{	
		for ( int i = 0 ; i < filter.length ; i ++ )
			Db.delete(filter[i]);
		
		DeviceReportTest.report(nuid, report, null);
		
		Db.commit();
		
		Db.sleep(5 * 1000);
		
		Db.init();
		
		for ( int i = 0 ; i < filter.length ; i ++ )
			Assert.assertEquals(1, Db.count(filter[i]));
	}
	
	private static TestConnectionContext textcontext = new TestConnectionContext();
	public static void report(int nuid , byte[] report , Date time)
	{
		if ( time == null )
			time = new Date();
		byte[] b = createReport(report ,nuid , time);
		
		ZWaveReportProcessor pro = new ZWaveReportProcessor();

		try {
			pro.process(b, textcontext) ;//new TestConnectionContext());
		} catch (BufferOverflowException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static int sequence = 1 ;
	
	public static byte[] createReport(byte[] report , int nuid  , Date time)
	{		
		CommandTlv ct = new CommandTlv(30 , 9);
		
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvByteUnit(70 , report));
		ct.addUnit(new TlvIntUnit(104,(int)(time.getTime()/1000) , 4));
		ct.addUnit(new TlvIntUnit(31,sequence ++ , 4));
		return ct.getByte();
	}
	
	public static int getnuIdLenght(int nuid)
	{
		if ( nuid >= 10000 )
			return 4 ;
		return 1 ;
	}
}
