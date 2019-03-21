package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ADT extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ADT";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ADT(ADT) 1
"00e047003382338110281f2a65291f291f281f281f281f291f2a1f291f291f291f291f29202820281f291f29652965286529202920282028202865281f281f29202866286628652966298994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}