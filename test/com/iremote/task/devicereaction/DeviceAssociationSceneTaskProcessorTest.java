package com.iremote.task.devicereaction;

import com.iremote.domain.ZWaveDevice;

public class DeviceAssociationSceneTaskProcessorTest {

	public static void main(String arg[])
	{
		ZWaveDevice device = new ZWaveDevice();
		device.setDevicetype("5");
		device.setZwavedeviceid(5);
		//DeviceAssociationSceneTaskProcessor pro = new DeviceAssociationSceneTaskProcessor(device , 0 , 1 , 0);
		//pro.run();
	}
}
