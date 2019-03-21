package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sonolor extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sonolor";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Sonolor(Sonolor) 1
"00e04700338234810f291f291f291f29652865296528202820286528652866291f291f291f29652865291f2a1f2965296528652820282028202865296529202820281f296529652865288994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}