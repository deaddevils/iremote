package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Nanpingcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Nanpingcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 南平有线(Nanping cable) 1
"00e0470033823381102a1f281f281f291f291f291f292028652866296529652966286529652865291f291f2965291f28202866291f292028652866282028662865282028662966281f288998823c8085280000000000000000000000000000000000000000000000000000000000",
};
}