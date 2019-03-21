package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jinchengradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jinchengradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 晋城广电(Jincheng radio and television) 1
"00e0470033823481112820291f281f281f291f291f291f292028652866286628652a6529652865296528202820286628202865291f2a1f291f2965286629202865281f29662965286529899b823b8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 晋城广电(Jincheng radio and television) 2
"00e04700338234811028202920291f291f281f2920281f28202866286628662865286529652966286529202820286628202866281f281f2a1f2965296528202866282028652966296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}