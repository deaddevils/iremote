package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Atlantic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Atlantic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 亚特兰大(Atlantic) 1
"00e02f0037333176323d333e323d323e313d333e323e663d3278333d95c73d3177333e323d323e323d323e323e313d683e3276323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 亚特兰大(Atlantic) 2
"00e02f003736313e3277313d323e313e323d323d323e673d3177323e95c43e323d3277313d323e333d323e323e313d683e3276323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}