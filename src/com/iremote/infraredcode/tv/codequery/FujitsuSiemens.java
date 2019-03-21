package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class FujitsuSiemens extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "FujitsuSiemens";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 富士通西门子(Fujitsu Siemens) 1
"00e02f0037363176323d323d323e313d323e313e323d683d3277313d95c23d3175323d323e323d323d313d323e323d673e3276313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}