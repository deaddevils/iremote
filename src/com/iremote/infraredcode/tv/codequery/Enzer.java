package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Enzer extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Enzer";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∂˜‘Û(Enzer) 1
"00e04700338232811028202865291f2965291f281f281f281f291f291f291f291f291f291f281f281f281f29652820282028662820281f28202866281f296529652820286528662865298993823c8086280000000000000000000000000000000000000000000000000000000000",
};
}