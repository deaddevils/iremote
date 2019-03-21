package cn.com.isurpass.gateway.server.processor.lock;

import com.iremote.common.TagDefine;
import com.iremote.infraredtrans.ProcessorStore;

public class LockProcessorStore extends ProcessorStore
{
	private static LockProcessorStore instance = new LockProcessorStore();

	public static LockProcessorStore getInstance()
	{
		return instance ;
	}
	
	protected LockProcessorStore()
	{
		super();
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_GATEWAY_LOGIN ,TagDefine.COMMAND_SUB_CLASS_GATEWAY_LOGIN_RESPONSE), LockLoginProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_ENCRYPT ,TagDefine.COMMAND_SUB_CLASS_SECURITYKEY_RESPONSE), LockSecurityKeyProcessor.class);

	}
}