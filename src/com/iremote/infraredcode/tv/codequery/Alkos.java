package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Alkos extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Alkos";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Alkos(Alkos) 1
"00e04700338234810f2920282028202820282028202865282028662965296528652965286628202865281f2866281f291f2965291f281f292028652a1f29652865281f296528662865298994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}