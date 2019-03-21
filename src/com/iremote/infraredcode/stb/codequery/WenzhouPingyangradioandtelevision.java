package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class WenzhouPingyangradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "WenzhouPingyangradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 温州平阳广电(Wenzhou Pingyang radio and television) 1
"00e0470033823381102965281f2820281f292028202820281f2965291f296529652965286528672866281f2920296629652965291f296528662866286629202820291f2965291f281f29899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}