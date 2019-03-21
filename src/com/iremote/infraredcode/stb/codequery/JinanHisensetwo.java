package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JinanHisensetwo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JinanHisensetwo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 济南海信二(Jinan Hisense two) 1
"00e0470033823481112866291f281f281f291f291f291f2920281f28662866286529652965296628652965286728202820281f281f291f2a1f291f292028652867286628652866286529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}