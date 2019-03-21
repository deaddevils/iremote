package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Shanghaicabletwo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Shanghaicabletwo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 上海有线二(Shanghai cable two) 1
"00e02f003732646e3038643830383038306d31386438303930392f39944d39646d3039633830383138306d31396338313830392f39000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}