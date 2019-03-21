package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShandongZiboDongguan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShandongZiboDongguan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 山东-淄博/东莞(Shandong Zibo Dongguan) 1
"00e047003382328110281f281f2a1f291f291f291f281f28202866286628652a65296528652965286628202820286629652965281f2920281f2866286528212820282029652865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}