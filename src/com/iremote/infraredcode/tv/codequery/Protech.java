package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Protech extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Protech";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Protech(Protech) 1
"00e02f00340c81311381341281d01181331181d11281331381d2118133118133118133118134128c431381341281331281d01181331281d11281331381d1128133118133118133118133110000000000000000000000000000000000000000000000000000000000000000000000",
};
}