package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tianjinradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tianjinradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 天津广电(Tianjin radio and television) 1
"00e04700338232811028652820291f281f2920281f291f2920286528202966296528652a652965286529202820286628662865281f2a652965296629662820281f28202966291f281f28899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}