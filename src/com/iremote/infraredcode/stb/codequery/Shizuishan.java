package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Shizuishan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Shizuishan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Ê¯×ìÉ½(Shizuishan) 1
"00e0470033823581112966292028202820291f2920291f291f2965281f286628652866286628652966291f291f29662966286528202866286529652965281f2920281f28662820281f28899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}