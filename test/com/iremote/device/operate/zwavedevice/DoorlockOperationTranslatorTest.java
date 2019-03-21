package com.iremote.device.operate.zwavedevice;

import com.iremote.common.Utils;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;

public class DoorlockOperationTranslatorTest
{
	public static void main(String arg[])
	{
		Db.init();
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(5);
		
		DoorlockOperationTranslator t = new DoorlockOperationTranslator();
		t.setZWavedevice(zd);
		t.setCommandjson("[{\"operation\":\"doorlockopen\",\"channelid\":0}]");
		
		Utils.print("" , t.getCommand());
	}
}
