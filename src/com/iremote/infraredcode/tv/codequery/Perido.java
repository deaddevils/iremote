package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Perido extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Perido";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Ã‚(Perido) 1
"00e02f00340b81331281321181d01281d01181d11281331281331181341281d1128133128133128c441281331281331381d01281d21281d01281341281331181331181d0128133118134120000000000000000000000000000000000000000000000000000000000000000000000",
};
}