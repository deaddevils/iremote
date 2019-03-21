package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Qinzhou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Qinzhou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//ЛњЖЅКа Чежн(Qinzhou) 1
"00e047003382328110282028202820282028202820282028642865296529652865296528662866281f281f2865282028202865281f282028662965291f296528652920296628662820288995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}