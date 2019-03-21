package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ESC extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ESC";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ESC(ESC) 1
"00e02f0037373276323d333d313e323e313d323e323d673e3276313d95c53d3277323e323e323d323e313d323e323d683d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}