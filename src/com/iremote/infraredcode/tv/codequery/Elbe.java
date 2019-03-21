package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Elbe extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Elbe";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 易北河(Elbe) 1
"00e02f003737323d3277333d313e323d323d323e323d673e3276313d95c63f313d3278323d323e313d323e333d323d673e3176313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}