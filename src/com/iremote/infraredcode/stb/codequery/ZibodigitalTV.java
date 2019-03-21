package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ZibodigitalTV extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ZibodigitalTV";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 淄博数字电视(Zibo digital TV) 1
"00e04700338233811028202820281f29202920291f291f281f28662966286528662866286529662965291f281f2966286528662820281f281f2965296529202820281f28662866296628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}