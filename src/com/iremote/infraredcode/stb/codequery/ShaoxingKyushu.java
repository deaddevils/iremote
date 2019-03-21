package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShaoxingKyushu extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShaoxingKyushu";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÉÜÐË¾ÅÖÝ(Shaoxing Kyushu) 1
"00e047003382348110281f2920291f291f291f2966281f296628662820286628652965291f2965282028202866281f28652820291f281f281f28652920286528212866286628652a6529899b823c8086280000000000000000000000000000000000000000000000000000000000",
};
}