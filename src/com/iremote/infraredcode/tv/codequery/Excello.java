package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Excello extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Excello";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∞¨øÀ(Excello) 1
"00e02f003737313d3278333d323e323d323d333e323d673e3277313e95c63e323d3377323d313d333d323e323d313d683e3377323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}