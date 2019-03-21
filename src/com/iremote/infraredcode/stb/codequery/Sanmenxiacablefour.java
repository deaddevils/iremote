package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sanmenxiacablefour extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sanmenxiacablefour";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 三门峡有线四(Sanmenxia cable four) 1
"00e047003382328110291f281f291f291f292028652820286529652920286628652965281f2865291f2a1f2965281f2865291f2920291f291f2965281f2965291f2965286529652866288995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}