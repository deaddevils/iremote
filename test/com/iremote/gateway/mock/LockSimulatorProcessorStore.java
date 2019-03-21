package com.iremote.gateway.mock;

import com.iremote.common.TagDefine;
import com.iremote.common.processorstore.ClassMapper;
import com.iremote.gateway.mock.processor.GatewaySimulatorLoginProcessor;
import com.iremote.gateway.mock.processor.GatewaySimulatorReportSecurityKeyProcessor;
import com.iremote.gateway.mock.processor.LockSimulatorLoginProcessor;
import com.iremote.gateway.mock.processor.LockSimulatorReportSecurityKeyProcessor;
import com.iremote.infraredtrans.IRemoteRequestProcessor;

public class LockSimulatorProcessorStore extends ClassMapper<IRemoteRequestProcessor>
{
	private static LockSimulatorProcessorStore instance = new LockSimulatorProcessorStore();

	public static LockSimulatorProcessorStore getInstance()
	{
		return instance ;
	}
	
	protected LockSimulatorProcessorStore()
	{
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_GATEWAY_LOGIN , TagDefine.COMMAND_SUB_CLASS_GATEWAY_LOGIN_REQUEST), LockSimulatorLoginProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_ENCRYPT , TagDefine.COMMAND_SUB_CLASS_SECURITYKEY_REQUEST), LockSimulatorReportSecurityKeyProcessor.class);
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
