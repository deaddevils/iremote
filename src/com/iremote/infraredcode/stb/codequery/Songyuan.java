package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Songyuan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Songyuan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ËÉÔ­(Songyuan) 1
"00e047003382358110296528202820282028202820281f281f2965291f296528662966286629662866281f291f2a652965286629202865286528662865291f2a1f291f29652820282028899b823b8088280000000000000000000000000000000000000000000000000000000000",
};
}