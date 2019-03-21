package com.iremote.event.gateway;

import java.util.List;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOnlineEvent;

public class NotifyGatewayOnlineEventConsumerProcessor extends RemoteOnlineEvent implements ITextMessageProcessor
{

	@Override
	public void run()
	{
		List<IGatewayEventConsumer> lst = GatewayEventConsumerStore.getInstance().get(super.getDeviceid());
		
		if ( lst == null )
			return ;
		
		for ( IGatewayEventConsumer c : lst)
			c.onGatewayOnline();
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}

}
