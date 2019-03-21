package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Carad extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Carad";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� Carad(Carad) 1
"00e02f0037313277323e313e323e313d323e323d323d673d3176313e95c93e3276323d333e323d323d313d333e323d663d3277333e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� Carad(Carad) 2
"00e02f003735313d3278323d323d323e313e323e323e663d3377323d95ca3d313d3377323d323e323d313e323e313d683d3376333d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}