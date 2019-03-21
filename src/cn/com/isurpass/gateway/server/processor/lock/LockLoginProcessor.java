package cn.com.isurpass.gateway.server.processor.lock;

import cn.com.isurpass.gateway.server.processor.BaseLoginProcessor;
import cn.com.isurpass.gateway.server.processor.IGatewaySecurityKey;

public class LockLoginProcessor extends BaseLoginProcessor 
{
	@Override
	protected IGatewaySecurityKey getSecurityKey() 
	{
		return new TeaGatewaySecurityKey();
	}

	protected boolean checkSecurityKey(byte[] request)
	{
		return true;
	}
}
