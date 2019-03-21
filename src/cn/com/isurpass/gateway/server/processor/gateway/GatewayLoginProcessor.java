package cn.com.isurpass.gateway.server.processor.gateway;

import cn.com.isurpass.gateway.server.processor.BaseLoginProcessor;
import cn.com.isurpass.gateway.server.processor.IGatewaySecurityKey;

public class GatewayLoginProcessor extends BaseLoginProcessor 
{
	@Override
	protected IGatewaySecurityKey getSecurityKey() 
	{
		return new AesGatewaySecurityKey();
	}

}
