package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Beon extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Beon";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ‘⁄(Beon) 1
"00e02f003735323d3276323d323e323d323e323d313e673e3276323d95c73d333d3277323e323d313e323d323d323e663d3377323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}