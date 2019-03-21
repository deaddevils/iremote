package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Vexa extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Vexa";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Vexa(Vexa) 1
"00e02f00376d313e323d323d323e323d323e313d323e683e3277313d947576323e323d323d333d313e323e313d333e673e3276323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}