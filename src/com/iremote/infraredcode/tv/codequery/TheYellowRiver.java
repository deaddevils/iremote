package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class TheYellowRiver extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "TheYellowRiver";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ª∆∫”(The Yellow River) 1
"00e02f003731323d3277323d333d323e323d323d333d673d3277323d95c43d333d3177323e313d323e323d323d323e663d3377323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}