package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JiulianTechnology extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JiulianTechnology";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 九联科技(Jiulian Technology) 1
"00e0470033822f8111286528662865281f2a1f291f29652820286728202820281f291f2a1f2965291f28202820282028202866291f29202a1f2965296629662865282128662866296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}