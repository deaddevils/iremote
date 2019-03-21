package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dongyingradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dongyingradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 东营广电(Dongying radio and television) 1
"00e04700338232811028652820291f281f2920281f291f2920282028662966296528652965296528652965286628202820281f281f281f2a1f291f292028652867286628652966296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 东营广电(Dongying radio and television) 2
"00e0470033823381102865282129202820291f281f281f291f281f29662865286628662865296629652965296528202820282028202820281f281f281f2a652965286629662866296628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}