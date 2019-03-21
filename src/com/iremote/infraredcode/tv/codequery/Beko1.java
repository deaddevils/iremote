package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Beko1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Beko1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ±¥ø∆ (Beko) 1
"00e02f00340b81341281331281d01281331281d21281331281d0128133128133118133128133128c431181321181321281d11381331381d11181331181d0128133128133138134128133120000000000000000000000000000000000000000000000000000000000000000000000",
};
}