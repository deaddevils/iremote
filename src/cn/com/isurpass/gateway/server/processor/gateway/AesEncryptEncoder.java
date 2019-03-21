package cn.com.isurpass.gateway.server.processor.gateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.TagDefine;
import com.iremote.infraredtrans.Remoter;

import cn.com.isurpass.gateway.server.processor.BaseEncryptEncoder;

public class AesEncryptEncoder extends BaseEncryptEncoder {

	private static Log log = LogFactory.getLog(AesEncryptEncoder.class);
	
	@Override
	protected int getEncryptTag() 
	{
		return TagDefine.TAG_AES_ENCRYPT;
	}

	@Override
	protected byte[] encrypt(Remoter remoter, byte[] content) 
	{
		try 
		{
			return GatewayAES.encrypt(remoter.getKey3() , content);
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
		}
		return null ;
	}

}
