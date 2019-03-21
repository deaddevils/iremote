package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Exquisit extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Exquisit";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ±ðÖÂ(Exquisit) 1
"00e02f0037373176313e323e313d323e313e333e313d673d3277323d95c53d3177333e323d323e323d333e323d323d683e3177323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}