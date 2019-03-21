package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Xinjiangradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Xinjiangradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 新疆广电(Xinjiang radio and television) 1
"00e047003582338111282028202865291f2a1f291f2965282028672866281f29652965281f291f28652965286628202866281f291f2a1f291f291f292028662820286628652866286528899b823b8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 新疆广电(Xinjiang radio and television) 2
"00e0470033823381112920281f28662820281f291f2966291f2865286529202865286728202820286529662965292028652920281f281f2820282028202965281f2a6529652866286628899b823c8087280000000000000000000000000000000000000000000000000000000000",
//机顶盒 新疆广电(Xinjiang radio and television) 3
"00e047003582338110281f282028202866281f281f2865291f2965286629662820286528662865291f2a1f296529202866282028202820281f2965291f29652920286529652867286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
//机顶盒 新疆广电(Xinjiang radio and television) 4
"00e037003382358110291f291f291f29642864281f2963291f291f281f28202820286329202864281f28632864286428202864281e28632820288bc6823b808728000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}