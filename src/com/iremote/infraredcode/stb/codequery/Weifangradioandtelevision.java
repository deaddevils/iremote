package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Weifangradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Weifangradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 潍坊广电(Weifang radio and television) 1
"00e047003382358110296529202820281f282029202820291f2865291f296529652867286628662966291f281f28652965296528202866286629652866281f281f281f2965291f281f28899d823b8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 潍坊广电(Weifang radio and television) 2
"00e0470033823281102865291f291f281f2920281f281f29202865281f29662965286529652965286629202820286629662965281f2a6529652866286628202820291f2966291f281f28899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}