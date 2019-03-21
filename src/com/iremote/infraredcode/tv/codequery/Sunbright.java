package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sunbright extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sunbright";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Sunbright(Sunbright) 1
"00e03300348093212c204f204f214f202b2150202c2050202c202d212c202c85e1809a202b204f2150204f202b204f202b204f202b202b202c202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}