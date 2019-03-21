package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Foshancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Foshancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 佛山有线(Foshan cable) 1
"00e04700338232810f2820281f2820282028202820281f281f28652966296528652966286628642865282028202866296528662820281f281f29652866281f281f281f2a6529652865288996823b8085280000000000000000000000000000000000000000000000000000000000",
};
}