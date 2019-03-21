package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Celebrity extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Celebrity";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 名人(Celebrity) 1
"00e0330034808c2150202d2050202c204f212b202b204f202b202b212c202c85e1809a2050202c2050202c204f202b202c2050202b212b202b202c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}