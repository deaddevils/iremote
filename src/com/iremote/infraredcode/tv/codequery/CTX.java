package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class CTX extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "CTX";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ªÙ¬“(CTX) 1
"00e04700338232810f291f291f291f2920281f28662865291f29652865296528662866281f281f2866286529662820281f281f281f291f292028202820296528652866286529652965288993823b8087280000000000000000000000000000000000000000000000000000000000",
};
}