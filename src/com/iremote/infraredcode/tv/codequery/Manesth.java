package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Manesth extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Manesth";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Manesth(Manesth) 1
"00e04700338234811129202820281f281f291f2920282029652864286528662965291f29652865292028202866282028652820281f282028202866281f286528202865296529652864288994823b8087280000000000000000000000000000000000000000000000000000000000",
};
}