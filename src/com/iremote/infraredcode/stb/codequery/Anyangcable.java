package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Anyangcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Anyangcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 安阳有线(Anyang cable) 1
"00e0470033823481102920281f2865291f29202820291f291f281f28652a1f296529652865292028662820281f2865296628662820281f281f286628662820281f281f286528652965298996823c80862a0000000000000000000000000000000000000000000000000000000000",
//机顶盒 安阳有线(Anyang cable) 2
"00e04700338233810f282028202865291f29202820291f291f281f2966291f296529652965291f2965281f281f296529652965281f291f2a1f29652965281f291f2a1f296529652865298996823b8087290000000000000000000000000000000000000000000000000000000000",
};
}