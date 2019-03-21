package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Camry extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Camry";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” º—√¿(Camry) 1
"00e04700338234810f2820286528662865291f291f282028202820286628652866281f281f292028202820281f2865282028662820281f282028652965292028652820286529652965288995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}