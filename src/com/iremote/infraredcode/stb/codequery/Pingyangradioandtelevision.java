package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Pingyangradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Pingyangradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 平阳广电(Pingyang radio and television) 1
"00e0470033823481102865291f291f281f2920281f281f29202865281f29662965286529652965286629202820286629662965281f2a652965286628662820281f281f2966291f281f28899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}