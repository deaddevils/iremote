package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Akira extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Akira";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 阿基拉(Akira) 1
"00e02f0037363176333e323d323d333d323e323d323d673e3377323d95c93e3176313e333e313d323f333d323d323e673e3177323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}