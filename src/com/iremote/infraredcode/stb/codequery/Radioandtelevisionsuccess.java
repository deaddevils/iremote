package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Radioandtelevisionsuccess extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Radioandtelevisionsuccess";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 广电创佳(Radio and television success) 1
"00e047003382348110296728202820281f281f281f2a1f291f291f291f2865286628662965296528652a65291f291f2820282128202820291f281f286529652965286629662866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}