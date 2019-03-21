package com.iremote.device.operate.zwavedevice;

import com.iremote.common.Utils;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;

public class ZWaveACOperationTranslatorTest
{
	public static void main(String arg[])
	{
		Db.init();
		ZWaveACOperationTranslator t = new ZWaveACOperationTranslator();
		
		t.setCommandjson("[{\"mode\":5,\"fan\":0,\"temperature\":25,\"power\":1,\"winddirection\":2,\"autodirection\":1}]");
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(1653);
		t.setZWavedevice(zd);
		
		Utils.print("", t.getCommands());
	}
}
