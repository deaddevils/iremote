package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Foshanwithtwo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Foshanwithtwo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 佛山同州二(Foshan with two) 1
"00e047003382348110281f281f291f291f291f291f29202820286629652965286529652866286528652820282028662865286628202820282028652865282028202820286528662865298996823b8087280000000000000000000000000000000000000000000000000000000000",
};
}