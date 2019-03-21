package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sonitron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sonitron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” sonitron(sonitron) 1
"00e04700338233810f281f28202820286629652965281f281f296528662866281f281f282028662965291f291f28652865296628202820282028652866291f291f291f296528652965288993823b8086280000000000000000000000000000000000000000000000000000000000",
};
}