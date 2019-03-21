package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShandongLinyi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShandongLinyi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ É½¶«-ÁÙÒÊ(Shandong Linyi) 1
"00e04700338233811128202820281f28652a1f291f296528202866286528662820286628652a65291f2920286629202866281f29202920291f2965292028652920286528662866286529899c823c80862a0000000000000000000000000000000000000000000000000000000000",
};
}