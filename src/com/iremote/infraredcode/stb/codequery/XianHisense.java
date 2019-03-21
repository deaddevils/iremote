package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class XianHisense extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "XianHisense";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 西安海信(Xian Hisense) 1
"00e0470033823481112866291f281f281f281f291f29202820281f2865286628652965296529662866286528662820281f281f281f2a1f291f291f292028652866286629652866286529899b823b8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 西安海信(Xian Hisense) 2
"00e047003382348111296628202820281f291f2920291f291f291f296629662865286628662865296629652966291f2920281f281f282028202820291f28652a65296528662866286528899c823b8087290000000000000000000000000000000000000000000000000000000000",
};
}