package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Beaumark extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Beaumark";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Beaumark(Beaumark) 1
"00e04700338234810f281f2920286528202820281f2820282028652866281f286529652865286529662820281f281f2865292028202820281f28652965286628202865286628652965298995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}