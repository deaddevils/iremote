package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Anqiucable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Anqiucable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 安丘有线(Anqiu cable) 1
"00e0470033823581102a1f29202820281f29202820286629202920291f29652966291f2965282028202866281f28652a1f291f291f2965282028202866281f296529652865291f296529899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}