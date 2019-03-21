package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hebeiradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hebeiradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 河北广电(Hebei radio and television) 1
"00e04700338233811128202820291f28652a1f291f291f2920281f28672866281f281f29652965296628652965286728202820281f28652a1f291f291f28202866286628662920286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}