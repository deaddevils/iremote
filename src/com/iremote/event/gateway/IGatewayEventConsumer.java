package com.iremote.event.gateway;

import com.iremote.common.store.IExpire;

public interface IGatewayEventConsumer extends IExpire
{
	void onGatewayOnline();
	void onGatewayOffline();
}
