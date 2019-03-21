package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Meizhoucable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Meizhoucable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 梅州有线(Meizhou cable) 1
"00e047003582328110281f291f29202920291f291f281f281f28652966286628652866286529652966291f2a1f2965291f2965281f291f2a1f29652965281f29652820286628652865288996823b8087280000000000000000000000000000000000000000000000000000000000",
};
}