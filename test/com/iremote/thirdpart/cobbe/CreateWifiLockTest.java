package com.iremote.thirdpart.cobbe;

import com.iremote.test.db.Db;
import com.iremote.test.db.Env;
import com.iremote.thirdpart.cobbe.event.CreateWifiLock;

public class CreateWifiLockTest
{
	public static void main(String arg[])
	{
		Env.getInstance().need();
		Db.init();
		
		CreateWifiLock cwl = new CreateWifiLock();
		cwl.setDeviceid("iRemote3006100000242");
		
		cwl.run();
		Db.commit();
	}
}
