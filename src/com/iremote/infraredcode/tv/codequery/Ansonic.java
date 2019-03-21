package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Ansonic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Ansonic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” øÁ“ÙÀŸ(Ansonic) 1
"00e02f003c0c8201138155118221128221128221118221128221128155128221128155128155118994118203128155128221128221128221138222128221128155128221128155128155120000000000000000000000000000000000000000000000000000000000000000000000",
};
}