package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Calix extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Calix";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ”  •≤Õ±≠(Calix) 1
"00e04700338232810f291f296528642865282028652965291f2865291f2a1f2a1f2965281f281f28652920282028662820286528202820281f29652965281f28652920296628662865288995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}