package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class YunnanHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "YunnanHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 云南华为(Yunnan Huawei) 1
"00e047003382358110291f292028202866282028202965281f2a6529652866281f296628662866281f281f2865291f296528202820282028202866291f2965291f296528652965286528899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}