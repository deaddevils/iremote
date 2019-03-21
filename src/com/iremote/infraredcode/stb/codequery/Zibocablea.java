package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zibocablea extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zibocablea";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 淄博有线一(Zibo cable a) 1
"00e04700338234811028202a1f296529202820281f2920282028662966291f286529652965286628662820282028652866281f281f291f291f2966286528202820286628652a65296528899b823c8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 淄博有线一(Zibo cable a) 2
"00e047003382348111292028202866281f281f291f2a1f291f29652866292028652865286628652965291f291f2966286628202820281f292029662965291f291f296628652866286629899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}