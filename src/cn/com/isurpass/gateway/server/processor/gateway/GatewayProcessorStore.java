package cn.com.isurpass.gateway.server.processor.gateway;

import com.iremote.common.TagDefine;
import com.iremote.infraredtrans.ProcessorStore;

public class GatewayProcessorStore extends ProcessorStore
{
	private static GatewayProcessorStore instance = new GatewayProcessorStore();

	public static GatewayProcessorStore getInstance()
	{
		return instance ;
	}
	
	protected GatewayProcessorStore()
	{
		super();
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_GATEWAY_LOGIN ,TagDefine.COMMAND_SUB_CLASS_GATEWAY_LOGIN_RESPONSE), GatewayLoginProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_ENCRYPT ,TagDefine.COMMAND_SUB_CLASS_SECURITYKEY_RESPONSE), GatewaySecurityKeyProcessor.class);

	}
}
