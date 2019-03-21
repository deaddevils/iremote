package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Electroband extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Electroband";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Electroband(Electroband) 1
"00e0330034808d2150202c204f212b204f212c202b214f202c202b212c202b85e2809a2050202c2050202b214f202b202b204f202c212b202b202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}