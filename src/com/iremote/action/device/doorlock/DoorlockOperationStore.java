package com.iremote.action.device.doorlock;

import com.iremote.common.store.SingleObjectStore;

public class DoorlockOperationStore extends SingleObjectStore<IDoorlockOperationProcessor>
{
	private static DoorlockOperationStore instance = new DoorlockOperationStore();
		
	private DoorlockOperationStore()
	{
	}
	
	public static DoorlockOperationStore getInstance()
	{
		return instance ;
	}
	
}
