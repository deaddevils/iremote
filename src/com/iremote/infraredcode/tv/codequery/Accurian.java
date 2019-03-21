package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Accurian extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Accurian";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Accurian(Accurian) 1
"00e04700338234810f2a1f28652865292028202820281f28652965282028662820281f281f2820282028662865281f2866281f291f291f291f291f29202866281f2965296528642864288995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}