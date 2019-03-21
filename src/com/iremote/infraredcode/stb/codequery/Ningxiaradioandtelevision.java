package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Ningxiaradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Ningxiaradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 宁夏广电(Ningxia radio and television) 1
"00e04700338233811028662820291f291f2a1f291f291f29202866292028662865286628652965296529202820286628652865282029652865296529652820281f29202866281f292029899c823b80862a0000000000000000000000000000000000000000000000000000000000",
//机顶盒 宁夏广电(Ningxia radio and television) 2
"00e0470033823581102965291f281f282128202820291f281f2865291f296528662866286528652866281f281f29652965286628202866286629662965281f2a1f291f29652820282028899b823b8086280000000000000000000000000000000000000000000000000000000000",
};
}