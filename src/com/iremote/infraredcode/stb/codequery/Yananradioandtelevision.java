package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yananradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yananradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 延安广电(Yanan radio and television) 1
"00e0470033823381112920281f2966282028652820291f291f2865291f291f29202865282028202820281f281f28652965296528202866286528662866281f281f2a1f2965291f282028899c823c8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 延安广电(Yanan radio and television) 2
"00e047003382348110291f291f2865292028652820282028202865291f2a1f291f296528202820282028202820286529662965281f28652965286628662820281f291f2965291f291f29899c823c8086290000000000000000000000000000000000000000000000000000000000",
};
}