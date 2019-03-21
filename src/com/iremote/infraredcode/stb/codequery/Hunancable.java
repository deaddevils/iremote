package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hunancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hunancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 湖南有线(Hunan cable) 1
"00e0470033823481112920282028202820281f281f2965291f291f292028662966281f28652820291f2865291f2965291f281f282028662820281f28652a1f2965296628662820286528899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}