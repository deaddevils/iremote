package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ZunyiHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ZunyiHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 遵义华为(Zunyi Huawei) 1
"00e04700338234810f281f291f291f2865291f292029662820286529652865291f296528652965281f281f286428202866291f291f291f291f2965281f296428202865296529652865288993823a8086280000000000000000000000000000000000000000000000000000000000",
};
}