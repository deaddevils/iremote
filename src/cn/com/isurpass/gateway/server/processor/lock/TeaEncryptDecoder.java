package cn.com.isurpass.gateway.server.processor.lock;

import com.iremote.common.TagDefine;
import com.iremote.common.encrypt.Tea;

import cn.com.isurpass.gateway.server.processor.BaseEncryptDecoder;

public class TeaEncryptDecoder extends BaseEncryptDecoder 
{
	public static final int TIMES = 32 ;
	
	@Override
	protected int getEncryptTag() 
	{
		return TagDefine.TAG_TEA_ENCRYPT;
	}

	@Override
	protected byte[] decipher(byte[] key , byte[] content) 
	{
		return Tea.decryptByTea(content, key);//  .decrypt(content, 0, key, TIMES);
	}

}
