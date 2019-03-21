package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Mudanjiangcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Mudanjiangcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 牡丹江有线(Mudanjiang cable) 1
"00e047003382348111291f281f2a1f291f291f291f291f28202866296529652865296528662866286428202820286628652866281f291f292028652866281f291f2920286528662865298995823c8086280000000000000000000000000000000000000000000000000000000000",
};
}