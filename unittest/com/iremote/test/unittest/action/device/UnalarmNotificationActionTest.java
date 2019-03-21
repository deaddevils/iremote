package com.iremote.test.unittest.action.device;

import com.iremote.action.notification.UnalarmNotificationAction;
import com.iremote.service.PhoneUserService;
import com.iremote.test.data.ConstDefine;
import com.iremote.test.data.ConstDefine_DoorSensor;
import com.iremote.test.db.Db;
import com.iremote.test.db.Env;

public class UnalarmNotificationActionTest {

	public static void main(String arg[])
	{
		Env.getInstance().need();
		Db.init();
		UnalarmNotificationAction action = new UnalarmNotificationAction();
		
		action.setDeviceid(ConstDefine.DEVICE_ID);
		action.setNuid(ConstDefine_DoorSensor.NUID_DOORSENSOR);
		
		PhoneUserService pus = new PhoneUserService();
		action.setPhoneuser(pus.query(1));
		
		action.execute();
		
		Db.commit();
	}
}
