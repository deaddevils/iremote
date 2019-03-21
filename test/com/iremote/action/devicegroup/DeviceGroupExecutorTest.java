package com.iremote.action.devicegroup;

import com.iremote.service.DeviceGroupService;
import com.iremote.service.PhoneUserService;
import com.iremote.test.db.Db;

public class DeviceGroupExecutorTest
{
	public static void main(String arg[])
	{
		Db.init();
		
		PhoneUserService pus = new PhoneUserService();
		DeviceGroupService dgs = new DeviceGroupService();
		
		DeviceGroupExecutor dge = new DeviceGroupExecutor(pus.query(1), dgs.get(7),"{\"operation\":\"open\"}");
		dge.run();
		
		Db.rollback();
	}
}
