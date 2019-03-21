package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Syntax extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Syntax";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ”Ô∑®(Syntax) 1
"00e04700338232810f281f28202866291f291f291f291f291f28652865292028652965296528652964281f282028202865291f281f2920281f296528662866281f2866286529662866288993823a8085280000000000000000000000000000000000000000000000000000000000",
};
}