package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Konka1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Konka1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¿µ¼Ñ(Konka)-SDC25 1
"00e0470033823381112920282028202866281f291f2965291f296528652965281f28672866286529202a1f29652920286629202820281f281f2966291f2865291f296529652867286628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}