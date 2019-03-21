package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class TheNorth extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "TheNorth";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ±±¹ã(The North) 1
"00e04700338234811028202820281f281f291f2a1f291f291f286528672866286528662865296529652920286629202865281f29202820291f2965291f28652920286528662866286529899c823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}