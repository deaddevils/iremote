package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GuangxiradioandTVnetwork extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GuangxiradioandTVnetwork";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 广西广电网络(Guangxi radio and TV network) 1
"00e04700338232810f291f291f281f2920291f291f291f29652965286529662866286528662865291f291f2965281f281f2a65291f291f28652865292028662866281f28662865291f298995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}