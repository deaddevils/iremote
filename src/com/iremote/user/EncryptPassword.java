package com.iremote.user;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@Deprecated
public class EncryptPassword {

	private static final byte[] key = new byte[]{48,(byte)129,(byte)159,(byte)48,(byte)13,(byte)6,(byte)9,(byte)42,(byte)134,(byte)72,(byte)134,(byte)247,(byte)13,(byte)1,(byte)1,(byte)1,(byte)5,(byte)0,(byte)3,(byte)129,(byte)141,(byte)0,(byte)48,(byte)129,(byte)137,(byte)2,(byte)129,(byte)129,(byte)0,(byte)210,(byte)182,(byte)105,(byte)136,(byte)245,(byte)66,(byte)69,(byte)78,(byte)74,(byte)138,(byte)236,(byte)56,(byte)21,(byte)240,(byte)43,(byte)33,(byte)81,(byte)255,(byte)49,(byte)237,(byte)178,(byte)163,(byte)196,(byte)245,(byte)135,(byte)90,(byte)94,(byte)78,(byte)12,(byte)40,(byte)10,(byte)207,(byte)2,(byte)220,(byte)211,(byte)54,(byte)13,(byte)7,(byte)238,(byte)117,(byte)104,(byte)77,(byte)226,(byte)14,(byte)144,(byte)169,(byte)196,(byte)27,(byte)148,(byte)128,(byte)252,(byte)110,(byte)0,(byte)96,(byte)71,(byte)144,(byte)159,(byte)241,(byte)1,(byte)150,(byte)13,(byte)173,(byte)96,(byte)20,(byte)95,(byte)150,(byte)36,(byte)141,(byte)42,(byte)9,(byte)137,(byte)140,(byte)112,(byte)199,(byte)129,(byte)251,(byte)67,(byte)113,(byte)66,(byte)108,(byte)132,(byte)72,(byte)169,(byte)3,(byte)67,(byte)187,(byte)129,(byte)6,(byte)49,(byte)230,(byte)204,(byte)59,(byte)78,(byte)23,(byte)39,(byte)237,(byte)148,(byte)8,(byte)131,(byte)91,(byte)91,(byte)152,(byte)219,(byte)104,(byte)18,(byte)248,(byte)96,(byte)184,(byte)115,(byte)194,(byte)90,(byte)160,(byte)191,(byte)11,(byte)207,(byte)80,(byte)94,(byte)6,(byte)218,(byte)102,(byte)186,(byte)76,(byte)46,(byte)47,(byte)96,(byte)6,(byte)217,(byte)93,(byte)2,(byte)3,(byte)1,(byte)0,(byte)1};
	public static final String KEY_ALGORITHM = "RSA";
	
	public static byte[] encrypt(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(password.getBytes());
	}
	
	
}
