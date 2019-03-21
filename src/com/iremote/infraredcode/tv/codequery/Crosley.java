package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Crosley extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Crosley";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 克罗斯利(Crosley) 1
"00e03300338092204f202b204f202b2050202c202b2150202c202c212c202b85e3809a204f212c2050202b204f202c212b214f212c202b202b212b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}