package com.iremote.common.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MD5Util 
{
		private static Log log = LogFactory.getLog(MD5Util.class);

	    public static boolean checkRemote(String deviceid , String token , byte[] secreykey , byte[] check) 
	    {
	    	if ( deviceid == null || token == null || secreykey == null || check == null )
	    		return false ;
	    				
			byte[] id = deviceid.getBytes();
			byte[] bt = token.getBytes();
			
			byte[] m = new byte[secreykey.length + id.length + bt.length];
			
			System.arraycopy(id, 0 , m, 0 , id.length);
			System.arraycopy(secreykey, 0 , m, id.length , secreykey.length);
			System.arraycopy(bt, 0 , m, id.length + secreykey.length , bt.length);
			
			try {
				MessageDigest mdInst = MessageDigest.getInstance("MD5");
				for ( int i = 0 ; i < 129 ; i ++ )
				{
					mdInst.update(m);
					m = mdInst.digest();
				}
			} catch (NoSuchAlgorithmException e) {
				log.error(e.getMessage() , e);
			}
			
			if ( m.length != check.length )
				return false ;
			
			for ( int i = 0 ; i < check.length ; i ++ )
			{
				if ( m[i] != check[i] )
					return false ;
			}
			return true;
	    }
	    public static String MD5(String str){  
			StringBuffer buf = new StringBuffer("");
			SimpleDateFormat s = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");  
				md.update(str.getBytes());
				byte b[] = md.digest();
				int i;

				for (int offset = 0; offset < b.length; offset++) {
						i = b[offset];
						if(i<0) i+= 256;
						if(i<16)
							buf.append("0");
						buf.append(Integer.toHexString(i));
				}
				//System.out.println("result: " + buf.toString());//32bit
				//System.out.println("result: " + buf.toString().substring(8,24));//16bit
			}catch (Exception e) {   
				e.printStackTrace();
			}
			return buf.toString();
	    } 
		public static byte[] md5(byte[] content , int times)
		{
			for ( int i = 0 ; i < times ; i ++ )
				content = DigestUtils.md5(content);
			return content;
		}

}
