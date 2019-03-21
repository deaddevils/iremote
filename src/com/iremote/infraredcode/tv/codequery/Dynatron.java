package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dynatron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dynatron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¸º×è(Dynatron) 1
"00e02f003737323d3277323e313e333d323e323e313e683e3276323e95c43d323e3176323e323d323d313d333e323d673e3277313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}