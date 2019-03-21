package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JinanHisensethree extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JinanHisensethree";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 济南海信三(Jinan Hisense three) 1
"00e0470033823481102866281f291f2a1f291f291f2920282028212866286629652965286529652965286629662820281f29202920291f291f281f282028662865286628662865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 济南海信三(Jinan Hisense three) 2
"00e047003382348110296628202820281f281f281f2a1f291f291f2965286728662865296629652865296529652820282028202820281f281f291f291f29652866286628652866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}