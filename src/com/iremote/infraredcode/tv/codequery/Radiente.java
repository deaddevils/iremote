package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Radiente extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Radiente";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” radiente(radiente) 1
"00e02f0037363277313d323e323d333d323d323e313d683d3276323e95c23d3176323e313e323d323d323e313e323d673e3277313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}