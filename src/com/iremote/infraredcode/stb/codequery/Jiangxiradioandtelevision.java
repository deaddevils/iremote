package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jiangxiradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jiangxiradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 江西广电(Jiangxi radio and television) 1
"00e0470035823381102865282028202820281f281f291f2a1f291f2920281f286628662865296529652865281f291f29202820281f28202920291f296529662965296528672866286528899b823b8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 江西广电(Jiangxi radio and television) 2
"00e0470033823481102865291f291f29202820281f282028202820281f291f29662965296629662865286728202820281f291f2a1f291f291f291f2866286628662965286628652a6529899b823d8086290000000000000000000000000000000000000000000000000000000000",
};
}