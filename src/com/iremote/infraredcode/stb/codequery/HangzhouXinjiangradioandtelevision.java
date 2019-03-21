package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HangzhouXinjiangradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HangzhouXinjiangradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 杭州/新疆广电(Hangzhou / Xinjiang radio and television) 1
"00e0470033823481112920282028202866281f291f2a65291f29662866286528202866286628652a1f291f29652820286628202820281f29202966291f2865291f296628652866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}