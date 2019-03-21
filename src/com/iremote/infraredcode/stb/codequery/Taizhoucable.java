package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Taizhoucable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Taizhoucable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 泰州有线(Taizhou cable) 1
"00e0470033823481102865291f291f291f28202820282028202866281f29652965296628662865286728202820286529652965292028662966286628662820281f281f2a65291f291f28899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}