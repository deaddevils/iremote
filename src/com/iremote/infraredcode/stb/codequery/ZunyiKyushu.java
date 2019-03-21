package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ZunyiKyushu extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ZunyiKyushu";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ×ñÒå¾ÅÖÝ(Zunyi Kyushu) 1
"00e04700338232810f291f291f291f2920292028652a1f29652865281f286528662965291f2965281f291f296528202865291f291f282028202866291f2965291f2865296428652965298993823b8087280000000000000000000000000000000000000000000000000000000000",
};
}