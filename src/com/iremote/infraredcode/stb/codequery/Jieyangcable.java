package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jieyangcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jieyangcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ½ÒÑôÓÐÏß(Jieyang cable) 1
"00e0470035823381102966282028202820281f281f2a1f2a1f296529202866296628662966296528652a1f291f296528662966281f2865286628652a65291f291f282028662920282028899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}