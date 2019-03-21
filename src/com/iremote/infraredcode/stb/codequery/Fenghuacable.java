package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Fenghuacable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Fenghuacable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 奉化有线(Fenghua cable) 1
"00e0470033823481102820281f29202920291f291f281f292028652965286628662866296529652865281f291f2965286728662820281f291f2a6529652920281f282028662866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}