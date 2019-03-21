package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Euroopa extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Euroopa";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Euroopa(Euroopa) 1
"00e02f0037363376323d323e313d333d313e323e313d673d3176323e95c23e3277323d313d333d323e323d323d333d673e3377323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}