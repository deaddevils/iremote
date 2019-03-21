package com.iremote.event.gateway;

import com.iremote.common.store.MultiObjectStore;

public class GatewayEventConsumerStore extends MultiObjectStore<IGatewayEventConsumer>
{
	private static GatewayEventConsumerStore instance = new GatewayEventConsumerStore();
	
	private GatewayEventConsumerStore()
	{
		
	}
	
	public static GatewayEventConsumerStore getInstance()
	{
		return instance ;
	}
}
