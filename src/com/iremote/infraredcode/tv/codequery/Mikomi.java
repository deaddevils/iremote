package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Mikomi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Mikomi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Mikomi(Mikomi) 1
"00e02f00366f313d333d323e323d313d333d323d323e673e3276323e947176313d333d333d323d313d333e323d323e673e3176323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}