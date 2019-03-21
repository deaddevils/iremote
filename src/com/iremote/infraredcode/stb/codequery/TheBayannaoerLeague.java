package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class TheBayannaoerLeague extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "TheBayannaoerLeague";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºĞ °Í©Ä×¶ûÃË(The Bayannaoer League) 1
"00e0470035823381112920281f28202820281f281f291f2a1f29652966286529652867286628652965291f2965292028652920281f281e28202866281f28652a1f296529652867286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
//»ú¶¥ºĞ °Í©Ä×¶ûÃË(The Bayannaoer League) 2
"00e0470035823381112920282028202820281f281f281f2a1f296529652866296628662966296528652a1f29652920286629202820281f281f2966291f2965291f296529652867286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}