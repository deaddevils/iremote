package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zaozhuangradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zaozhuangradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 枣庄广电(Zaozhuang radio and television) 1
"00e047003382338110281f282028662820281f281f281f2a1f2965291f2820282028662820281f291f291f2965291f2966281f2966281f281f2966291f28652a1f2965291f2866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
//机顶盒 枣庄广电(Zaozhuang radio and television) 2
"00e047003382358110291f291f2865282028202820281f281f2965291f291f29202866282028202820281f2965291f29652920286529202820286628202866281f2865291f2965286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}