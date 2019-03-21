package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zhangzhouradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zhangzhouradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 漳州广电(Zhangzhou radio and television) 1
"00e04700338233810f2865291f292028202820281f292028202865282028662965296528652a652866281f281f2865296628662820286528662965296529202820281f2965291f291f298996823b8086290000000000000000000000000000000000000000000000000000000000",
};
}