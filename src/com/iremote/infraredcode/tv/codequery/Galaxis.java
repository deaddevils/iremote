package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Galaxis extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Galaxis";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 星际总动员(Galaxis) 1
"00e02f003732313e3277313d323e313e323d323d323e663d3377323d95c33d323e3177333d333d323d313d333d323d673e3276323c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}