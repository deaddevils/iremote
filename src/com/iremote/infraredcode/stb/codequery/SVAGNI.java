package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class SVAGNI extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "SVAGNI";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¹ãµçGNI(SVA GNI) 1
"00e0470033823481112966282028202820281f291f291f2a1f291f29652866296628662866286628652a652965281f291f29202820281f281f2920282029652865296529652867286628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}