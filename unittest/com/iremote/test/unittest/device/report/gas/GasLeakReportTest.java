package com.iremote.test.unittest.device.report.gas;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.iremote.test.data.ConstDefine_Gas;
import com.iremote.test.db.Db;
import com.iremote.test.db.Env;
import com.iremote.test.unittest.device.report.DeviceReportTest;

public class GasLeakReportTest {

	public static void main(String arg[]) throws ServletException
	{
		GasLeakReportTest grt = new GasLeakReportTest();
		grt.setUp();
		DeviceReportTest.report(ConstDefine_Gas.NUID_GAS, ConstDefine_Gas.GAS_ALARM, null);
	}
	
	@Before
	public void setUp() throws ServletException
	{
		Env.getInstance().need();
		Db.init();
		Db.update(ConstDefine_Gas.INIT_GAS_DEVICE, ConstDefine_Gas.INIT_GAS_DEVICE_FILTER);
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
		DeviceReportTest.testReport(ConstDefine_Gas.NUID_GAS, ConstDefine_Gas.GAS_ALARM, ConstDefine_Gas.FILTER_GAS);

	}
}
