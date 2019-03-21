package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Haibeistate extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Haibeistate";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 海北州(Haibei state) 1
"00e0470033823581102a1f29202820281f292028202820281f2965296528652965296528672866286629202820296528652965291f2820282028662866291f291f2a1f29652966286628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}