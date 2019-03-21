package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShanghaiOrientalCablea extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShanghaiOrientalCablea";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �Ϻ���������һ(Shanghai Oriental Cable a) 1
"00e02f003731636d3138633930392f39306c3038633830392f383139944d38636d3038643830383038306d31396438303930393039000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ �Ϻ���������һ(Shanghai Oriental Cable a) 2
"00e02f003730646c303863382f383038306c30396339303830393038944e38646c303963382f383139306c2f396339303830383038000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}