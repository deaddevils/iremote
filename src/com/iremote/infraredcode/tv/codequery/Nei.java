package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Nei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Nei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ƒ⁄√…π≈(Nei) 1
"00e02f00340b81341281d01181d11181331281d01181331181d1128133138134128133128133128c451281331281d01281d01181331181d01281331181d1118134128133128133128133120000000000000000000000000000000000000000000000000000000000000000000000",
};
}