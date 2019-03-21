package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Foshanstate extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Foshanstate";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 佛山同州(Foshan state) 1
"00e047003382358110291f292028202820282028202820281f28652a65296528652966286528662866291f291f2a652965286529202820281f28652866281f281f2a1f29652965286629899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}