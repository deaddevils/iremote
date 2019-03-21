package com.iremote.thirdpart.rentinghouse.task;

import com.iremote.common.thread.ThreadManager;

public class ExpireZwaveDeviceShareTaskTest {

	public static void main(String arg[])
	{
		ThreadManager.keepRunning("ExpireZwaveDeviceShareTask", new ExpireZwaveDeviceShareTask());
	}
}
