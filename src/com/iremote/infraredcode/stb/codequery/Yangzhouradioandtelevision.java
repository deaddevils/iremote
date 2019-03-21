package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yangzhouradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yangzhouradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 扬州广电(Yangzhou radio and television) 1
"00e047003382348110281f2a1f291f29652820281f28662820286528662865291f29652965286629202820286629202966291f281f2920281f2966281f28662820286628652a65296528899c823c8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 扬州广电(Yangzhou radio and television) 2
"00e047003382338110291f291f29202866282028202866291f296629652865291f2965286628662820281f2965291f29652920281f291f2920286528202866281f28652a652965286629899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}