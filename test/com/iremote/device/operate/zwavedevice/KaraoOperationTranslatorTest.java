package com.iremote.device.operate.zwavedevice;

import com.iremote.common.Utils;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;

public class KaraoOperationTranslatorTest {
	public static void main(String[] args) {
		Db.init();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(2041);
		
		KaraoOperationTranslator n = new KaraoOperationTranslator();
		n.setZWavedevice(zd);
		String json = "[{power:255,mode:1,musicvolume:59,micvolume:69,effectsvolume:49}]";
		n.setCommandjson(json);
		
		System.out.println("######################################");
		byte[] bs = n.getCommand();
		Utils.print("", bs);

		byte[] by = new byte[]{0,70,0,3,37,1,(byte)255,0,72,0,1,0,0,74,0,3,37,3,(byte)255,0,71,0,1,49,0,70,0,4,43,1,1,0,0,72,0,1,0,0,71,0,1,49,0,70,0,7,(byte)96,13,0,1,38,1,59,0,72,0,1,0,0,74,0,7,(byte)96,13,1,0,38,3,59,0,71,0,1,49,0,70,0,7,(byte)96,13,0,2,38,1,59,0,72,0,1,0,0,74,0,7,(byte)96,13,2,0,38,3,59,0,71,0,1,49,0,70,0,7,(byte)96,13,0,3,38,1,59,0,72,0,1,0,0,74,0,7,(byte)96,13,3,0,38,3,59,0,71,0,1,49};
		n.setCommand(bs);
		n.setCommandjson(null);
		System.out.println();
		System.out.println(n.getCommandjson());
	}
}
