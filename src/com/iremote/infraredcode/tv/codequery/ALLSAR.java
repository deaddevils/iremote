package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ALLSAR extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ALLSAR";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ALLSAR(ALLSAR) 1
"00e02f003734313e3277313d323e323d323e323e313e673e3277313d95c93d323d3176313e323e313d333d323d323d673e3176323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}