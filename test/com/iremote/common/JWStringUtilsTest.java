package com.iremote.common;

import com.alibaba.fastjson.JSON;

public class JWStringUtilsTest
{
	public static void main(String arg[])
	{
		//System.out.println(JWStringUtils.toHexString(new byte[]{0x02,0x1c,0x50,0x00,0x10,0x00}));
		
		Object json = JSON.parse("{\"loginname\":\"0050001\",\"password\":\"123456\",\"comnutiyid\":1}");
		
		System.out.println(json.getClass().getName());
	}
}
