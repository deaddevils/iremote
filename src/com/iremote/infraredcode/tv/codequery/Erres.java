package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Erres extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Erres";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Erres(Erres) 1
"00e02f003736323e3176323e323d323e313d323e323d673e3277313d95c43e333d3277323d323d333d323e323d323d683e3177323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}