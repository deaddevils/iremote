package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Fujimaru extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Fujimaru";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ÃŸÕË(Fujimaru) 1
"00e047003382338110281f29202920291f291f2865281e281f292028202820282028202820286528202820281f291f291f2920281f281f282028662965286628662865286629652866288993823a8085280000000000000000000000000000000000000000000000000000000000",
};
}