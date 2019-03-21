package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Ironskycable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Ironskycable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 铁岒天光有线(Iron 岒 sky cable) 1
"00e0470033823381102a65281f281f291f29202820281f281f2966291f286529652965286628662866281f2920296629652965291f296528662866286629202820291f2965291f281f29899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}