package com.iremote.device;

import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;

public class ClearZwaveDeviceTest
{
	public static void main(String arg[])
	{
		Db.init();
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ClearZwaveDevice czd = new ClearZwaveDevice(zds.query(1741));
		czd.clear();
		
		Db.commit();
	}
}
