package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GuizhouZunyiCounty extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GuizhouZunyiCounty";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 贵州遵义同州(Guizhou Zunyi County) 1
"00e047003382358110291f2920281f286628202820281f281f281f2a65296528202820286628662965296528652a65291f291f2820286728202820281f281f2a6529652966291f296628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}