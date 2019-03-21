package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Changyuancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Changyuancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 长垣有线(Changyuan cable) 1
"00e0470033823481102865291f291f2920281f2820282028202866281f28652965296628662865286728202820286529652965292028662966286629662820281f281f2965291f291f28899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}