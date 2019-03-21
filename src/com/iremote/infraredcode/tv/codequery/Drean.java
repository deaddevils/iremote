package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Drean extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Drean";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” √ŒœÎ(Drean) 1
"00e02f0037333277323e313e323d323d323e323d323e673d3276333e95c53d3177333e313d323e333d323e323e313e683e3276323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}