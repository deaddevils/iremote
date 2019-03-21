package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Datsura extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Datsura";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Datsura(Datsura) 1
"00e04700338232810f282028202820286528662965291f291f296629652a652820281f281f2865296628202820286528662865291f2920281f28662865291f291f291f296629652a65288995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}