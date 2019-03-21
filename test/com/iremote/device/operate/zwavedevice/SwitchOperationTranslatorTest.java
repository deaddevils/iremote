package com.iremote.device.operate.zwavedevice;

import com.iremote.common.Utils;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;

public class SwitchOperationTranslatorTest
{
	public static void main(String arg[])
	{
		Db.init();
		SwitchOperationTranslator t = new SwitchOperationTranslator();
		t.setCommandjson("[{\"operation\":\"open\"}] ");
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(1473);
		t.setZWavedevice(zd);
		
		Utils.print("", t.getCommand());
	}
}
