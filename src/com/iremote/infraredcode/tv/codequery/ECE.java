package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ECE extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ECE";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ≈∑÷›(ECE) 1
"00e02f0037363177333e313d323e323d333e313d323e683e3177323e95c53f3176323d333d323e323d323d333e323d673e3277313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}