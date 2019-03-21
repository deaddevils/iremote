package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Radioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Radioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¹ãµç(Radio and television)-GNI 1
"00e0470033823581102a652920281f291f29202820281f281f2920286628652a65296528662866286528652866281f281f291f291f291f2920281f28202866286628652a652965286629899b823b8086290000000000000000000000000000000000000000000000000000000000",
};
}