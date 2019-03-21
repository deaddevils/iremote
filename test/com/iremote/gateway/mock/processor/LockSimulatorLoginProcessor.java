package com.iremote.gateway.mock.processor;

import com.iremote.common.TagDefine;
import com.iremote.common.encrypt.Tea;

import cn.com.isurpass.gateway.server.processor.IGatewaySecurityKey;
import cn.com.isurpass.gateway.server.processor.lock.TeaGatewaySecurityKey;

public class LockSimulatorLoginProcessor extends GatewaySimulatorLoginProcessor {

	private IGatewaySecurityKey skutil = new TeaGatewaySecurityKey();
	
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
	
	@Override
	protected byte[] encrypt(byte[] key, byte[] content) 
	{
		return Tea.encryptByTea(content, key);//  .encrypt(content, key, TeaEncryptDecoder.TIMES);
	}
}
