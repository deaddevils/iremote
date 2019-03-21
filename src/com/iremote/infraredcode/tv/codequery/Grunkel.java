package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Grunkel extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Grunkel";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Grunkel(Grunkel) 1
"00e02b003735333d3277323e313d323e6777323d673e3277313d95c53e323d3277323e313e323d6777323d683e3176323e00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}