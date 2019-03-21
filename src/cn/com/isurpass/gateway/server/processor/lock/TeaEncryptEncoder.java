package cn.com.isurpass.gateway.server.processor.lock;

import com.iremote.common.TagDefine;
import com.iremote.common.encrypt.Tea;
import com.iremote.infraredtrans.Remoter;

import cn.com.isurpass.gateway.server.processor.BaseEncryptEncoder;

public class TeaEncryptEncoder extends BaseEncryptEncoder {
	
	@Override
	protected int getEncryptTag() 
	{
		return TagDefine.TAG_AES_ENCRYPT;
	}

	@Override
	protected byte[] encrypt(Remoter remoter, byte[] content) 
	{
		return Tea.encryptByTea(content, remoter.getKey3(), TeaEncryptDecoder.TIMES);
	}
	

}
