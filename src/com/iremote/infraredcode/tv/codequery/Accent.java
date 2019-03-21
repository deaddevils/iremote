package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Accent extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Accent";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ø⁄“Ù(Accent) 1
"00e02f003735323d3277323e333d323e323e323d323e673e3176313e95c83e323e3176323d323e313d323f333d323d673e3277323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}