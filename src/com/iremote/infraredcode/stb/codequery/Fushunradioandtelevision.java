package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Fushunradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Fushunradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 抚顺广电(Fushun radio and television) 1
"00e0470033823481112966292028202820281f291f2a1f291f2965282028662865286528662865286529652965286529202820282029662820291f281f29202865296528662820286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
//机顶盒 抚顺广电(Fushun radio and television) 2
"00e04700338233811128662820281f291f2a1f291f291f291f28662820286628652966296528652965296628662865281f292028202965281f291f281f291f296528672866281f286529899c823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}