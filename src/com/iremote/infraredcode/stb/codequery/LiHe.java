package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class LiHe extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "LiHe";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Á¦ºÏ(Li He) 1
"00e047003382348110281f282029202820291f281f28652965296528672866286629662965281f2a1f2965291f28202820282028202820281f281f296529652966286628652865286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}