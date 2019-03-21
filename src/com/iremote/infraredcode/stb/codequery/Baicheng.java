package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Baicheng extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Baicheng";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ °×³Ç(Baicheng) 1
"00e0470033823481102965281f291f29202820281f282029202866281f28652965296528672866286628202820296528652965291f28662866286629662820291f281f2966291f292028899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}