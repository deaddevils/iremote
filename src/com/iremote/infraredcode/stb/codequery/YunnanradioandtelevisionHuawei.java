package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class YunnanradioandtelevisionHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "YunnanradioandtelevisionHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 云南广电华为(Yunnan radio and television Huawei) 1
"00e03d00341e202820286529202965286528652920286528672866281f291f2966291f2865281f291f29202820286628202866281f286529652965286629899b823b8087280000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}