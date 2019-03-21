package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Daytron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Daytron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Daytron(Daytron) 1
"00e02f003736333d3277323d313d333d313e323e313d683d3376333d95c43e323e3177323e313d323e313e333e313d673e3176313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}