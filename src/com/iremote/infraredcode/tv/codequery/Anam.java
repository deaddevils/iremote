package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Anam extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Anam";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 安南(Anam) 1
"00e02f003735333d3277323d323d323e323d323d333d683d3277323e95c63e323e3276313d333d333d323d323d323e673d3277323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}