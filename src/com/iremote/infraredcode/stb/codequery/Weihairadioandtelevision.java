package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Weihairadioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Weihairadioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 威海广电 (Weihai radio and television) 1
"00e047003382338110291f291f29202820281f281f2920282029652865296529652867286628662966291f281f2865291f29652820282028202866286529202a65291f29662966286528899d823b8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 威海广电 (Weihai radio and television) 2
"00e04700338233811128202820281f281f281f2a1f291f291f29652867286628652966296528652965291f2820286728202866291f29202a1f29652966291f2966281f28662866286529899b823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}