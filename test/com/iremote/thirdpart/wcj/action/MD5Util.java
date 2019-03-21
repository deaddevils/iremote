package com.iremote.thirdpart.wcj.action;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;  
public class MD5Util {  
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
			//System.out.println("result: " + buf.toString());//32位的加密
			//System.out.println("result: " + buf.toString().substring(8,24));//16位的加密
		}catch (Exception e) {   
			e.printStackTrace();
		}
		return buf.toString();
    } 
    public static void main(String[] args){  
        System.out.println(MD5Util.MD5("20121221"));  
        System.out.println(MD5Util.MD5("加密"));  
    }  
}  