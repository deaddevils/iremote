package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Picture extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Picture";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÃÀÈç»­(Picture)-r5 1
"00e04700338234811028202820281f291f2a1f291f291f2920286628202866286528662865281f2965291f291f2820286628202820281f291f2a6529652866291f296528662866286529899b823b8086290000000000000000000000000000000000000000000000000000000000",
};
}