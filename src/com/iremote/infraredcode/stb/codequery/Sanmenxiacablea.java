package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sanmenxiacablea extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sanmenxiacablea";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 三门峡有线一(Sanmenxia cable a) 1
"00e047003382348110291f281f291f281f291f291f2920281f286628652965296528652966296528642820296628202866281f28202920282028662820286428202866286628652866298995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}