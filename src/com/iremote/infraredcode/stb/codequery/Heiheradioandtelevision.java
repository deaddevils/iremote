package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Heiheradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Heiheradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 黑河广电(Heihe radio and television) 1
"00e0470033823381102965291f291f29202820281f2920282028652820286628662865286728662865282028202866286528652820286628652867286628202820281f29662820291f288997823c8087280000000000000000000000000000000000000000000000000000000000",
//机顶盒 黑河广电(Heihe radio and television) 2
"00e0470033823481102965291f29202820281f292028202820286528202866286528642866286628652821282028662865286628202866286528652966281f281f2820286628202820288996823b8087290000000000000000000000000000000000000000000000000000000000",
};
}