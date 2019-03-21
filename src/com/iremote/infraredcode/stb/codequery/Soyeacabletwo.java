package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Soyeacabletwo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Soyeacabletwo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 数源有线二(Soyea cable two) 1
"00e047003382338110291f291f2920281f282028202820282029652865296529662865296528662866281f281f286529652966281f29202820286629662820291f281f28652965296528899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}