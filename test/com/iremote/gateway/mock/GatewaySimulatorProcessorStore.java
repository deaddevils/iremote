package com.iremote.gateway.mock;

import com.iremote.common.TagDefine;
import com.iremote.common.processorstore.ClassMapper;
import com.iremote.gateway.mock.processor.GatewaySimulatorLoginProcessor;
import com.iremote.gateway.mock.processor.GatewaySimulatorReportSecurityKeyProcessor;
import com.iremote.infraredtrans.IRemoteRequestProcessor;

public class GatewaySimulatorProcessorStore extends ClassMapper<IRemoteRequestProcessor>
{
	private static GatewaySimulatorProcessorStore instance = new GatewaySimulatorProcessorStore();

	public static GatewaySimulatorProcessorStore getInstance()
	{
		return instance ;
	}
	
	protected GatewaySimulatorProcessorStore()
	{
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_GATEWAY_LOGIN , TagDefine.COMMAND_SUB_CLASS_GATEWAY_LOGIN_REQUEST), GatewaySimulatorLoginProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_ENCRYPT , TagDefine.COMMAND_SUB_CLASS_SECURITYKEY_REQUEST), GatewaySimulatorReportSecurityKeyProcessor.class);
	}
	
	public IRemoteRequestProcessor getProcessor(byte[] b , int index)
	{
		if ( b == null || b.length - index < 2 )
			return null ;
		return getProcessor(makeKey(b[index] & 0xff , b[index + 1] & 0xff));
	}
	
	protected String makeKey(int cmd , int subcmd)
	{
		return String.format("%d_%d", cmd , subcmd);
	}
}
