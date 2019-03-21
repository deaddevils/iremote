package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Guoluo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Guoluo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¹ûÂå(Guoluo) 1
"00e0470033823581112920281f292028202820281f291f292029652865286529652867286628662966291f291f286529652965282028202820286628652a1f291f291f29662866286528899d823b8086280000000000000000000000000000000000000000000000000000000000",
};
}