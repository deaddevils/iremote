package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Quanzhouradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Quanzhouradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 泉州广电(Quanzhou radio and television) 1
"00e04700338233810f2865281f28202820281f281f292028202865282028662965296528652965286628202820286528662965291f296528652965286628202820281f2866281f2820288995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}