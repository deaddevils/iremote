package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HarbinKnowall1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HarbinKnowall1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 哈尔宾-百事通(Harbin-Know-all) 1
"00e04700338233811028202820281f291f2965291f291f292028662966286628662820286529652965296629662820281f282029202820291f281f281f29652965286528662866296528899c823c80862a0000000000000000000000000000000000000000000000000000000000",
};
}