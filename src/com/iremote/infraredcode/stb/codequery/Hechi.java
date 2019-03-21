package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hechi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hechi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ºÓ³Ø(Hechi) 1
"00e047003382348110281f292028202820291f281f281f286529662866286528662965296628662820281f2864282028202866281f281f28662865291f29652865291f296628652920288994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}