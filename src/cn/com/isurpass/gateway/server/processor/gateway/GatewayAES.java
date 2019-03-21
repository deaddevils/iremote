package cn.com.isurpass.gateway.server.processor.gateway;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ServerRuntime;
import com.iremote.common.encrypt.AES;

public class GatewayAES {

	private static Log log = LogFactory.getLog(AES.class);
	
	//private static int length = 128;
	private static String password = ServerRuntime.getInstance().getDefaultkey();
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";  

	public static byte[] encrypt(String content) throws Exception
	{
		return encrypt(password.getBytes() , content.getBytes("utf-8"));
	}
	
	public static byte[] encrypt(byte[] pw , byte[] content)
	{
		try
		{
			SecretKeySpec key = new SecretKeySpec(pw, "AES");
	
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	
			cipher.init(Cipher.ENCRYPT_MODE, key);
	
			byte[] result = cipher.doFinal(content);
	
			return result; 
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
		return null ;
	}

	private static byte[] decrypt(byte[] content) throws Exception 
	{
		return decrypt(password.getBytes() , content);
	}
	

	public static byte[] decrypt(byte[] pw , byte[] content) 
	{
		try
		{
			SecretKeySpec key = new SecretKeySpec(pw, "AES");
	
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	
			cipher.init(Cipher.DECRYPT_MODE, key);
	
			byte[] result = cipher.doFinal(content);

			return result; 
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
		return null;

	}

	public static String encrypt2Str(String content) 
	{
		try
		{
			byte[] encryptResult = encrypt(content);

			return Base64.encodeBase64String(encryptResult);
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
		return null ;
	}

	public static String decrypt2Str(String content) 
	{
		try
		{
			byte[] decryptResult = decrypt(Base64.decodeBase64(content));

			return new String(decryptResult, "UTF-8");
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
		return null ;
	}
}
