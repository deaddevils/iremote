package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JinanHaierfour extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JinanHaierfour";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 济南海尔四(Jinan Haier four) 1
"00e0470033823581112966292028202820281f291f2920291f291f296528652965286728662866296529652865291f291f2920281f281f28202820282029652865296529652867286628899c823b8087280000000000000000000000000000000000000000000000000000000000",
//机顶盒 济南海尔四(Jinan Haier four) 2
"00e0470033823481102865282028202820281f281f281f2a1f291f29652866286628662966286628652a6529652820281f282028202820281f291f292029652965286529652867286628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}