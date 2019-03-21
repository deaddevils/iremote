package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class YunnanGNI extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "YunnanGNI";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÔÆÄÏGNI(Yunnan GNI) 1
"00e047003382358110296529202820282128202820281f281f291f2a6529652866296628652866286628652a65291f281f2820281f292028202820281f29652965286529652965286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}