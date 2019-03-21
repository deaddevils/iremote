package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class SichuanKyushu extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "SichuanKyushu";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ËÄ´¨¾ÅÖÝ(Sichuan Kyushu) 1
"00e0470033823581102a1f29202820281f29202866281f29662965281f296529652966281f28662820281f2965291f2965291f281f291f2920286528202966291f28652a652965286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}