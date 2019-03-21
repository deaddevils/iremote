package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class RadioandtelevisionHaier extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "RadioandtelevisionHaier";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 广电海尔(Radio and television Haier) 1
"00e0450034212028202866291f2920291f291f2920286629662820286528662865296529652966286529202865282028202820281f281f291f28652920286528662866286529899b823b80862a000000000000000000000000000000000000000000000000000000000000000000",
};
}