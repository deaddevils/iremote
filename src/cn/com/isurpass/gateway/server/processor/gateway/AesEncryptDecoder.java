package cn.com.isurpass.gateway.server.processor.gateway;

import com.iremote.common.TagDefine;

import cn.com.isurpass.gateway.server.processor.BaseEncryptDecoder;

public class AesEncryptDecoder extends BaseEncryptDecoder 
{	
	@Override
	protected int getEncryptTag() 
	{
		return TagDefine.TAG_AES_ENCRYPT;
	}

	@Override
	protected byte[] decipher(byte[] key , byte[] content) 
	{
		return GatewayAES.decrypt(key, content);
	}

}
