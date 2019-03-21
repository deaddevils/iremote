package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GuizhouNetcom extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GuizhouNetcom";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 贵州金网通(Guizhou Netcom) 1
"00e047003382348110291f291f281f291f29202820281f28202966296528652965296628652965286628202866281f2865291f291f291f2820286728202866291f296629652966296529899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}