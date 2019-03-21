package com.iremote.common.encrypt;

import com.iremote.common.Utils;
import com.iremote.common.md5.MD5Util;

public class AESTest
{
	public static void main(String arg[]) throws Exception
	{
		String s = AES.decrypt2Str("Zn7OWLLSz+Jn6G2lexJ36g==");
		System.out.println(s);
		String s1 = MD5Util.MD5(String.valueOf(4) + "1324");
		System.out.println(s1);
//		System.out.println(AES.encrypt2Str("890bb85b24254be4a6ef9a9f6a2af8"));
//		Utils.print("" , AES.encrypt("fdksjafkdjf"));
//		System.out.println(AES.decrypt2Str("q1HQtMSb3VmhkF10YJpEMA==") + ".");
//		System.out.println(AES.decrypt2Str("kBCtJfP94B6HrXL9FDYSaw==") + ".");
//		System.out.println(AES.decrypt2Str("yhBQ6IqqXkiVVlTNVja7pg==") + ".");
//		System.out.println(AES.decrypt2Str("ElcY9P/i0UnXUfulihbSHQ==") + ".");
//		System.out.println(AES.decrypt2Str("mpnWrvYyVyGAbiaF5ArT5Q==") + ".");
	}
}
