package com.iremote.test.unittest.device.report.doorlock;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.test.data.ConstDefine_Lock;
import com.iremote.test.db.Db;
import com.iremote.test.db.Env;
import com.iremote.test.unittest.device.report.DeviceReportTest;

import junit.framework.Assert;

public class DoorlookTest {

	public static void main(String arg[])
	{
		DeviceReportTest.report(ConstDefine_Lock.NUID_LOCK, ConstDefine_Lock.DOOR_LOCK_PASSWORD_ERROR_2, null);
	}
	
	@Before
	public void setUp() throws ServletException
	{
		Env.getInstance().need();
		Db.init();
		Db.update(ConstDefine_Lock.INIT_DOOR_LOCK_DEVICE, ConstDefine_Lock.INIT_DOOR_LOCK_DEVICE_FILTER);
		Db.commit();
		Db.init();
	}
	
	@After
	public void tearDown()
	{
		Db.commit();
	}
	
	@Test
	public void testReport()
	{	
		for ( int i = 0 ; i < ConstDefine_Lock.FILTER.length ; i ++ )
			Db.delete(ConstDefine_Lock.FILTER[i]);
		Db.delete(ConstDefine_Lock.FILTER_LOCK_CLOSE_TP_TEST);
		Db.delete(ConstDefine_Lock.FILTER_LOCK_OPEN_TP_TEST);
		Db.delete(ConstDefine_Lock.FILTER_LOCK_CLOSE_4);
		Db.commit();
		
		Db.init();
		
//		int s1 = TlvWrap.readInt(new byte[]{0 ,104 ,0 ,4, 88, 46, 120, (byte)155}, 104, 0);
//		int s2 = TlvWrap.readInt(new byte[]{0 ,104 ,0 ,4, 88 ,46, 123, (byte)167}, 104, 0);
//		DeviceReportTest.report(ConstDefine_Lock.NUID_LOCK, ConstDefine_Lock.DOOR_LOCK_CLOSE_1, Utils.parseTime(s1));
//		DeviceReportTest.report(ConstDefine_Lock.NUID_LOCK, ConstDefine_Lock.DOOR_LOCK_CLOSE_1, Utils.parseTime(s2));
//		DeviceReportTest.report(ConstDefine_Lock.NUID_LOCK, ConstDefine_Lock.report[2], null);
//		DeviceReportTest.report(ConstDefine_Lock.NUID_LOCK, ConstDefine_Lock.report[4], null);
		for ( int i = 0 ; i < ConstDefine_Lock.report.length ; i ++ )
		{
			DeviceReportTest.report(ConstDefine_Lock.NUID_LOCK, ConstDefine_Lock.report[i], null);
			Db.sleep(2*1000 + 100);
		}

		Db.sleep(500 * 1000);
		
		Db.commit();
		Db.init();
		
//		for ( int i = 0 ; i < ConstDefine_Lock.FILTER.length ; i ++ )
//			Assert.assertEquals(1, Db.count(ConstDefine_Lock.FILTER[i]));
//
//		Assert.assertEquals(3, Db.count(ConstDefine_Lock.FILTER_LOCK_CLOSE_4));
//		Assert.assertEquals(6, Db.count(ConstDefine_Lock.FILTER_LOCK_CLOSE_TP_TEST));
//		Assert.assertEquals(7, Db.count(ConstDefine_Lock.FILTER_LOCK_OPEN_TP_TEST));
	}
}
