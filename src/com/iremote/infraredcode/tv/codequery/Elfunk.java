package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Elfunk extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Elfunk";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Elfunk(Elfunk) 1
"00e02f0037373176323e323d323e313d323d323d323e673d3176323d95c33d3277323d323e323d323d313d333e323d673d3277323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}