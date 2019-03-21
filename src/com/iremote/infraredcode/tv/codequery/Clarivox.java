package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Clarivox extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Clarivox";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Clarivox(Clarivox) 1
"00e02f0037333277323d323e323d323d333d333d323d673e3177323e95c73e3176323e333e313d323e323d323e323e663d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}