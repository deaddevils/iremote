package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Clayton extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Clayton";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” øÀ¿≥∂Ÿ(Clayton) 1
"00e02f003736313e3277313d323e323d323e323e313e673e3277313d95c73f313e3176323d323e313d323e333d323d673e3176313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}