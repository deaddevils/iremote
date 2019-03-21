package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Funai extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Funai";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ¥¨æÆ(Funai) 1
"00e04700338234810f2920281f2965291f291f281f281f2865292028202820291f281f286529642865291f291f291f291f281f2865291f292028662866286528652965282028662865298993823b8086280000000000000000000000000000000000000000000000000000000000",
};
}