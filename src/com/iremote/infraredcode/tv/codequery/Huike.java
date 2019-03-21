package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Huike extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Huike";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ª›ø∆(Huike) 1
"00e0470033823481102820282028202820282028652820282028662865286629652a65291f28652865292028662820282028652820286529202865292028662965291f2965281f286529899782398086290000000000000000000000000000000000000000000000000000000000",
};
}