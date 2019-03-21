package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Friac extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Friac";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Friac(Friac) 1
"00e02f003736313d3377323d323d313d333d333d323d673e3177323e95c43e313d3278323d323e313d323d323d323e673e3176313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}