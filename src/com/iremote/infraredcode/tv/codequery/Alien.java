package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Alien extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Alien";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Õ‚–«(Alien) 1
"00e02f0037343277323d313d333d323e323d323d333d683d3277323e95c63f3276323d323d323e323d323d323d323e673e3176313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}