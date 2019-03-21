package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Centrum extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Centrum";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 中枢(Centrum) 1
"00e02f00376d313e333d323e323e313e333e313d323e673e3177323d947277313d323d323d323e323e313e323e313d673d3277323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 中枢(Centrum) 2
"00e02b00376c6777323e313d333e323d323d333d683d3276313e9474786776323d323e323d323e323e313e673e3276323d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 中枢(Centrum) 3
"00e02f003731323d3277323d333d313e323e313d323e673d3277323d95c53e313d3377323e313d323e313e323e313d673d3377323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}