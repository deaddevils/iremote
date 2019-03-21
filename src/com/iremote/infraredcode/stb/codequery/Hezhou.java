package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hezhou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hezhou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ºØÖÝ(Hezhou) 1
"00e0470033823281112920281f281f291f291f2920291f29652864286428652965296528652966282028202865281f282029652920282028652866281f29652965281f28642864281f288994823a8086280000000000000000000000000000000000000000000000000000000000",
};
}