package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class BayannaoerWuyuannumber extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "BayannaoerWuyuannumber";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 巴彦淖尔五原数(Bayannaoer Wuyuan number) 1
"00e047003382348110291f291f281f291f29202820282029202966296528652965296628662865286628202866281f2865291f291f291f2820286728202866291f296629652966296628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}