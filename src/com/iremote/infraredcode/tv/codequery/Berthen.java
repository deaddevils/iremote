package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Berthen extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Berthen";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Berthen(Berthen) 1
"00e02f003c0b8203128222128221138222128221128221118221128154118221128154118154118994128205128222128221128221128222128221128221118154128221118154138155120000000000000000000000000000000000000000000000000000000000000000000000",
};
}