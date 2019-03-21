package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Chaoyangcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Chaoyangcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ³¯ÑôÓÐÏß(Chaoyang cable) 1
"00e047003382348110291f291f29202820281f281f2920282028652866286529652966296529652965281f291f2a6529652865291f2920282029652865291f29202820296528652965288996823b8085280000000000000000000000000000000000000000000000000000000000",
};
}