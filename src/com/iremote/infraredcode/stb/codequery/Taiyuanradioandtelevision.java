package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Taiyuanradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Taiyuanradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 太原广电(Taiyuan radio and television) 1
"00e0470033823381112920282028662820281f291f2966291f28652865292028652867282028202865296629652920286529202820281f2820292028202965281f296529652866286628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}