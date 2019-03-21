package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class TaiyuanHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "TaiyuanHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 太原华为(Taiyuan Huawei) 1
"00e04700338232811028662820291f281f2920281f291f29202820281f2865286628652a652965296629662820281f282029202820291f281f2920286529652865286628662965286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}