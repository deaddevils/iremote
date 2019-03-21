package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sanmenxiacabletwo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sanmenxiacabletwo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 三门峡有线二(Sanmenxia cable two) 1
"00e0470035823381102865291f28202820281f281f292028202865282028662965296528652965286628202820286528662965291f29652865296528662820282028202864281f2820288995823a8086290000000000000000000000000000000000000000000000000000000000",
};
}