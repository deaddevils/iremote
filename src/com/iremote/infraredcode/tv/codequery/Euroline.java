package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Euroline extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Euroline";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Euroline(Euroline) 1
"00e02f00376e313d333d313e323d323d323e313e323e673d3276333e947176323e313d323e323d323e323e323e323d683d3277333d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}