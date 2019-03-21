package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Harbincabletwo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Harbincabletwo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 哈尔滨有线二(Harbin cable two) 1
"00e047003382348111281f281f2a1f291f291f291f2866281f281f292028652866281f28662820281f28642820286628202820291f29662820281f2865291f2a652965286529202866288996823b8087280000000000000000000000000000000000000000000000000000000000",
};
}