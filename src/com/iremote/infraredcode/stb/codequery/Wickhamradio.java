package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Wickhamradio extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Wickhamradio";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 广电威科姆(Wickham radio) 1
"00e047003382348111296728202820281f291f29202a1f291f291f2966286628652866286628652965291f291f2966286628202820281f292029662965291f291f296628652866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}