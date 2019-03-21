package cn.com.isurpass.gateway.server.processor.lock;

import cn.com.isurpass.gateway.server.processor.IGatewaySecurityKey;
import cn.com.isurpass.gateway.server.processor.BaseSecurityKeyProcessor;

public class LockSecurityKeyProcessor extends BaseSecurityKeyProcessor {

	@Override
	protected IGatewaySecurityKey getSecurityKey() 
	{
		return new TeaGatewaySecurityKey();
	}

}
