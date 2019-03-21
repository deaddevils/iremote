package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Domeos extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Domeos";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Domeos(Domeos) 1
"00e02f003c0d8200118155128221128221118221128221138221128154128222128155118154118994128204138156118221128221138222128221128221118154138221128154138156120000000000000000000000000000000000000000000000000000000000000000000000",
};
}