package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Waltham extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Waltham";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ »ªÉ­(Waltham) 1
"00e02f00340a81311281d01181d01281331181d11181341281d0128133118133128133128133118c471181331181d11281d01181331181d21081331281d0118134118133128133118134110000000000000000000000000000000000000000000000000000000000000000000000",
};
}