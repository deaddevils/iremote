package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Watson extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Watson";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Œ÷…≠(Watson) 1
"00e02f00376e323e313d333d333d323e323d323e323e673e3177323d947676323d323e323d323d333d323d323d333d673d3276323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}