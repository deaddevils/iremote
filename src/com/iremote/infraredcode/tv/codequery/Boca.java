package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Boca extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Boca";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ≤©ø®(Boca) 1
"00e02f003733323e3276313d333d323e323d6777313d333e323d323d95c63d323d3277323e323d313e323d6876323d333d323e323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}