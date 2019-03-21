package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Ningdecable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Ningdecable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 宁德有线(Ningde cable) 1
"00e04700338234810f28202820281f291f29202820291f296528652966296528652966286628642820292028662820281f28652920282029652865292029662965281f29652866281f288996823a80862a0000000000000000000000000000000000000000000000000000000000",
};
}