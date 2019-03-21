package com.iremote.action.device.doorlock;

import com.iremote.event.gateway.IGatewayEventConsumer;
import com.iremote.infraredtrans.zwavecommand.notifiy.IZwaveReportConsumer;

public interface IDoorlockOperationProcessor extends IZwaveReportConsumer,IGatewayEventConsumer
{
	int getStatus();
	String getMessage();
	boolean isFinished();
	
	void init();
	void sendcommand();
}
