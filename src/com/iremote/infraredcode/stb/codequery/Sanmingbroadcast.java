package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sanmingbroadcast extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sanmingbroadcast";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 三明广电(Sanming broadcast) 1
"00e047003382328110281f281f29202820282028202820286428642865296529652865296628662820282028642820282028652920281f286628652a1f29652865281f296528662820288995823a8086280000000000000000000000000000000000000000000000000000000000",
};
}