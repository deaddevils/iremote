package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Cathay extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Cathay";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” π˙Ã©(Cathay) 1
"00e02f0037363376323d323e333d313e323d323d323e673d3277323d95c43d3276323d323e313e333d323d323e323d673e3277313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}