package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Evesham extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Evesham";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” “¡∑Ú…·ƒ∏ (Evesham) 1
"00e02f0037363376323d323e313d323e323d323d323e663d3377323d95c43e3176313e333e313d323e313d333e323d673e3277313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}