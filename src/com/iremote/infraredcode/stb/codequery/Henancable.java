package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Henancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Henancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 河南有线(Henan cable) 1
"00e047003382348110286528202820291f291f281f2920281f29662820286628662865296529652965281f281f29662866286628202865296529652966291f2920282028662920282029899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}