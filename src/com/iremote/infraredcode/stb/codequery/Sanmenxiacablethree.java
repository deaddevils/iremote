package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sanmenxiacablethree extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sanmenxiacablethree";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 三门峡有线三(Sanmenxia cable three) 1
"00e04700338232810f2a65281f291f296628202820281f28652920296628662820286528662965291f291f29662a1f2a65291f291f281f281f28652920286628202864286428652965298995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}