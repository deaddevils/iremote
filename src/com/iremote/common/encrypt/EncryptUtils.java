package com.iremote.common.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ServerRuntime;

@Deprecated
public class EncryptUtils {
	
	private static Log log = LogFactory.getLog(EncryptUtils.class);
	private static String password = ServerRuntime.getInstance().getDefaultkey();
	
	public static String encrypt(String content) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
	        secureRandom.setSeed(password.getBytes("utf-8"));  
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
			
			return Base64.encodeBase64String(result); 
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public static String decrypt(String content) {
		try {
			Cipher cipher = createCipher();
			byte[] bc = Base64.decodeBase64(content);
			byte[] result = cipher.doFinal(bc);
			return new String(result,"utf-8"); 
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	
	private static Cipher createCipher() throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException
	{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
        secureRandom.setSeed(password.getBytes("utf-8"));  
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher;
	}
	
	public static void main(String arg[])
	{
		String str = encrypt("3.1415926a" );
		System.out.println(str);
		System.out.println(encrypt("3.1415926a" ));
		System.out.println(encrypt("3.1415926a" ));
		System.out.println(encrypt("3.1415926a" ));
		System.out.println(decrypt(str));
	}
}
