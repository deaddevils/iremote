package com.iremote.asiainfo.processor;

import com.iremote.infraredtrans.ProcessorStore;

public class AsiaInfoProcessorStore extends ProcessorStore {

	private static AsiaInfoProcessorStore instance = new AsiaInfoProcessorStore();

	public static ProcessorStore getInstance()
	{
		return instance ;
	}
	
	private AsiaInfoProcessorStore()
	{
		map.put(makeKey((byte)101 , (byte)3), AsiainfoSetRemoteOwerProcessor.class);
	}
	
	
}
