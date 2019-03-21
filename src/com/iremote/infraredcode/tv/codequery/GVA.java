package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GVA extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GVA";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 日内瓦(GVA) 1
"00e04700338234811028202820281f28202820282028202820286529652865296528652965291f2865291f291f29202820286628202820281f286628652a6528662820286528662a65288993823b8085280000000000000000000000000000000000000000000000000000000000",
};
}