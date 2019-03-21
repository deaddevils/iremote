package com.iremote.common.encrypt;

import org.apache.commons.codec.binary.Base64;

import com.iremote.common.Utils;

public class Base64Decoder {

	public static void main(String arg[])
	{
		byte[] b = Base64.decodeBase64("AEYAAyYBMgBIAAEAAEoAAyYDMgBHAAEXAE8AAQM=");
		Utils.print("fsaf", b);
	}
}
