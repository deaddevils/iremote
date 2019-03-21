package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Siping extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Siping";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ËÄÆ½(Siping) 1
"00e0470033823481102865291f291f292028202820282028202866291f29662965296629662865286728202820286529662965291f28652965286728662820281f291f2a65291f281f28899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}