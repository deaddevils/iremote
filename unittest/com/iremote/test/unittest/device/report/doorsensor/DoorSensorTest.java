package com.iremote.test.unittest.device.report.doorsensor;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.iremote.test.data.ConstDefine_DoorSensor;
import com.iremote.test.db.Db;
import com.iremote.test.db.Env;
import com.iremote.test.unittest.device.report.DeviceReportTest;

public class DoorSensorTest {
	
	public static void main(String arg[])
	{
		Env.getInstance().need();
		Db.init();
		DeviceReportTest.report(ConstDefine_DoorSensor.NUID_DOORSENSOR, ConstDefine_DoorSensor.DOOR_OPEN, null);
	}
	
	@Before
	public void setUp() throws ServletException
	{
		Env.getInstance().need();
		Db.init();
		Db.update(ConstDefine_DoorSensor.INIT_DOORSENSOR_DEVICE, ConstDefine_DoorSensor.INIT_DOORSENSOR_DEVICE_FILTER);
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
		DeviceReportTest.testReport(ConstDefine_DoorSensor.NUID_DOORSENSOR, ConstDefine_DoorSensor.DOOR_OPEN, ConstDefine_DoorSensor.FILTER_DOORSENSOR);

	}
}
