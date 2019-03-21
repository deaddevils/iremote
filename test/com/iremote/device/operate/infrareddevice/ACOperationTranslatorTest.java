package com.iremote.device.operate.infrareddevice;

import com.iremote.common.Utils;
import com.iremote.service.InfraredDeviceService;
import com.iremote.test.db.Db;

public class ACOperationTranslatorTest
{
	public static void main(String arg[])
	{
		Db.init();
		
		ACOperationTranslator t = new ACOperationTranslator();
		t.setCommandjson("[{\"power\": 0}]");
		
		InfraredDeviceService ids = new InfraredDeviceService();
		t.setInfrareddevice(ids.query(20));
		Utils.print("", t.getCommand());
	}
}
