package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Moons extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Moons";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÌìÃô(Moons)-LT300 1
"00e0470033823581102a1f29202820281f282028202820281f2965296528652865296528672866286629202920291f291f29202820281f28202866286528662865286529652966286628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}