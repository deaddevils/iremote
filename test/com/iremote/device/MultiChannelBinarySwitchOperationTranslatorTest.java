package com.iremote.device;

import com.iremote.common.Utils;
import com.iremote.device.operate.zwavedevice.MultiChannelBinarySwitchOperationTranslator;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;

public class MultiChannelBinarySwitchOperationTranslatorTest
{
	public static void main(String arg[])
	{
		Db.init();
		
		MultiChannelBinarySwitchOperationTranslator op = new MultiChannelBinarySwitchOperationTranslator();
		
		op.setChannelid(1);
		op.setCommandjson("[{\"value\":0,\"operation\":\"close\",\"channelid\":1}]");
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		op.setZWavedevice(zds.query(206));
		
		Utils.print("", op.getCommands());
	}
}
