package com.iremote.gateway.mock.processor;

import com.iremote.common.TagDefine;
import com.iremote.common.encrypt.Tea;

import cn.com.isurpass.gateway.server.processor.IGatewaySecurityKey;
import cn.com.isurpass.gateway.server.processor.lock.TeaEncryptDecoder;
import cn.com.isurpass.gateway.server.processor.lock.TeaGatewaySecurityKey;

public class LockSimulatorReportSecurityKeyProcessor extends GatewaySimulatorReportSecurityKeyProcessor
{
	private IGatewaySecurityKey skutil = new TeaGatewaySecurityKey();

	@Override
	protected byte[] encrypt(byte[] key, byte[] content) 
	{
		return Tea.encryptByTea(content, key);//  .encrypt(content, key, TeaEncryptDecoder.TIMES);
	}
	
	@Override
	protected IGatewaySecurityKey getSecurityKey()
	{
		return skutil;
	}
	
	@Override
	protected int getEncryptTag() 
	{
		return TagDefine.TAG_TEA_ENCRYPT;
	}
}
