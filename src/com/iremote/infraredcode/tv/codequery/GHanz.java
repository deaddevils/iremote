package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GHanz extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GHanz";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” GHanz(GHanz) 1
"00e047003382338110281f2920282028202820282028202820286528662965286529652965281f2864281f281f281f291f2965291f281f281f2865296528652965291f286528652866298992823a8087290000000000000000000000000000000000000000000000000000000000",
};
}