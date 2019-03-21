package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Goodrecord extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Goodrecord";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¼Ñ´´(Good record) 1
"00e04700338235811029652920281f2820282028202820281f281f281f2865296528662866286629662866281f281f291f291f2920281f281f2820286628652865296529652865296528899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}