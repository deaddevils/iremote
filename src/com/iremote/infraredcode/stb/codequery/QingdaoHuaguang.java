package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class QingdaoHuaguang extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "QingdaoHuaguang";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 青岛华光(Qingdao Huaguang) 1
"00e0470033823481102820281f28652a1f291f291f291f282028672866281f2965296528652965296528662866286629662820281f2865291f291f2920281f282028662866281f296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}