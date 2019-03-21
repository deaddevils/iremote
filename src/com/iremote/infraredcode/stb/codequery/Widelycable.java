package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Widelycable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Widelycable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 中广有线(Widely cable) 1
"00e0470033823481102865291f291f291f2820282028202820282028652965291f296528662966286628202866281f281f2a65291f291f28202867282028662965282029652865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}