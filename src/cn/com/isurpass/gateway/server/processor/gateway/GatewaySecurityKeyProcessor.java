package cn.com.isurpass.gateway.server.processor.gateway;

import cn.com.isurpass.gateway.server.processor.IGatewaySecurityKey;
import cn.com.isurpass.gateway.server.processor.BaseSecurityKeyProcessor;

public class GatewaySecurityKeyProcessor extends BaseSecurityKeyProcessor {

	@Override
	protected IGatewaySecurityKey getSecurityKey() 
	{
		return new AesGatewaySecurityKey();
	}

}
