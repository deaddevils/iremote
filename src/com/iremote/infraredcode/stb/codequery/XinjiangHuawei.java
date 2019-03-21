package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class XinjiangHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "XinjiangHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 新疆华为(Xinjiang Huawei) 1
"00e0470033823481112820291f291f2865291f291f2965282028662866286529202965286528652920281f286728202866281f291f2a1f291f296528202867282028662965296528652a899b823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}