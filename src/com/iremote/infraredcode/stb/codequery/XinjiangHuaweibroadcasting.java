package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class XinjiangHuaweibroadcasting extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "XinjiangHuaweibroadcasting";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 新疆华为广电(Xinjiang  Huawei broadcasting) 1
"00e047003382358110291f292028202866292028202965281f2965296528662820286628662966291f281f2865291f296528202820282028202866281f2965291f296528662966286528899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}