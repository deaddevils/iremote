package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Cosmel extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Cosmel";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Cosmel(Cosmel) 1
"00e02f0037373177323e323e313d333d333d323d333d673e3277323e95c63e3176313d333e323d323d313d333e323d663d3278333d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}