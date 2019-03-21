package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Telefunken extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Telefunken";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 德律风根(Telefunken) 1
"00e02f00330a81341181d11281d01281d01181d11281331281d01181d01281d01281331281d2118c441181341281d01081d01181d01281d01181331181d01281d01181d11281331281d0120000000000000000000000000000000000000000000000000000000000000000000000",
//电视 德律风根(Telefunken) 2
"00e02f00340e81311181341181d11281d01281d01181331181d01281d01281d01281341181d0118c441081331281331381d11281d01181d01281331281d01281d01181d11281341181d0110000000000000000000000000000000000000000000000000000000000000000000000",
//电视 德律风根(Telefunken) 3
"00e0110034220016522927299c46290017522a28140016000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 德律风根(Telefunken) 4
"00e00d003438522a12299c593f53291313001600000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}