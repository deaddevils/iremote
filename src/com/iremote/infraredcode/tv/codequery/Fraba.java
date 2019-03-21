package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Fraba extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Fraba";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Fraba(Fraba) 1
"00e02f0037353276323d333d323e323d323d333d323e673e3176313d95c53d3277313d323e323d323e313d323e323d683d3277333d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}