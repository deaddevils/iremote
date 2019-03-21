package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class SichuanWenzhou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "SichuanWenzhou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ËÄ´¨/ÎÂÖÝ(Sichuan Wenzhou) 1
"00e047003382358110291f291f2920281f282028662820281f28652a1f29652966281f282028202866291f2966291f286528652920281f2820286628202866291f291f29652965286529899b823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}